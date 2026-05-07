package app.services;

import app.data.Models.Cart;
import app.data.Models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

/**
 * Сервис для работы с корзиной.
 * Хранение данных — {@code app/data/carts.json}.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class CartService implements baseService<Cart> {
    private static final String FILE_PATH = "app/data/carts.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ProductService productService;
    private final UserService userService;

    public CartService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Заполняет transient поля {@code product} и {@code user} из соответствующих сервисов.
     *
     * @param item элемент корзины
     * @return тот же элемент с заполненными связями
     */
    public Cart resolve(Cart item) {
        item.product = productService.findById(item.product_id);
        item.user = userService.findById(item.user_id);
        return item;
    }

    /**
     * @return список всех элементов корзины, отсортированный по id
     */
    public List<Cart> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Cart>>() {
            }.getType();

            List<Cart> cart = gson.fromJson(reader, listType);

            if (cart == null)
                return new ArrayList<>();

            cart.forEach(this::resolve);
            cart.sort((a, b) -> Integer.compare(a.id, b.id));

            return cart;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * @param cart список элементов для сохранения
     * @throws RuntimeException если запись в файл не удалась
     */
    public void saveAll(List<Cart> cart) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cart, writer);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранения корзины: " + e.getMessage(), e);
        }
    }

    /**
     * Автоматически присваивает id как maxId + 1.
     *
     * @param item новый элемент корзины
     */
    public void add(Cart item) {
        List<Cart> cart = getAll();

        int maxId = cart.stream()
                .mapToInt(p -> p.id)
                .max()
                .orElse(0);

        item.id = maxId + 1;

        cart.add(item);
        saveAll(cart);
    }

    /**
     * @param userId идентификатор пользователя
     * @return список элементов корзины пользователя
     */
    public List<Cart> findByUserId(int userId) {
        return getAll().stream()
                .filter(c -> c.user_id == userId)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * @param id идентификатор элемента корзины
     * @return элемент корзины или {@code null} если не найден
     */
    public Cart findById(int id) {
        return getAll().stream()
                .filter(c -> c.id == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param id идентификатор элемента для удаления
     */
    public void deleteById(int id) {
        List<Cart> cart = getAll();
        cart.removeIf(c -> c.id == id);
        saveAll(cart);
    }

    /**
     * @param userId идентификатор пользователя чья корзина очищается
     */
    public void clearByUserId(int userId) {
        List<Cart> cart = getAll();
        cart.removeIf(c -> c.user_id == userId);
        saveAll(cart);
    }

    /**
     * @param userId идентификатор пользователя
     * @return сумма всех товаров в корзине пользователя
     */
    public Double getTotalByUserId(int userId) {
        return findByUserId(userId).stream()
                .mapToDouble(c -> c.price * c.quantity)
                .sum();
    }

    /**
     * @param id       идентификатор элемента корзины
     * @param quantity новое количество
     */
    public void updateQuantity(int id, int quantity) {
        List<Cart> cart = getAll();
        for (Cart item : cart) {
            if (item.id == id) {
                item.quantity = quantity;
                break;
            }
        }
        saveAll(cart);
    }
}
