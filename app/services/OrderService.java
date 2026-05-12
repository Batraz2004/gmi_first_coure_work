package app.services;

import app.data.Models.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

/**
 * Сервис для работы с заказами.
 * Хранение данных — {@code app/data/orders.json}.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class OrderService implements baseService<Order> {
    private static final String FILE_PATH = "app/data/orders.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ProductService productService;
    private final UserService userService;

    public OrderService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Заполняет transient поля {@code product} и {@code user} из соответствующих сервисов.
     *
     * @param order заказ
     * @return тот же заказ с заполненными связями
     */
    public Order resolve(Order order) {
        order.product = productService.findById(order.product_id);
        order.user = userService.findById(order.user_id);
        return order;
    }

    /**
     * @return список всех заказов, отсортированный по id
     */
    public List<Order> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Order>>() {
            }.getType();

            List<Order> orders = gson.fromJson(reader, listType);

            if (orders == null)
                return new ArrayList<>();

            orders.forEach(this::resolve);
            orders.sort((a, b) -> Integer.compare(a.id, b.id));

            return orders;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * @param orders список заказов для сохранения
     */
    public void saveAll(List<Order> orders) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(orders, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Автоматически присваивает id как maxId + 1.
     *
     * @param order новый заказ
     */
    public void add(Order order) {
        List<Order> orders = getAll();

        int maxId = orders.stream()
                .mapToInt(p -> p.id)
                .max()
                .orElse(0);

        order.id = maxId + 1;

        orders.add(order);
        saveAll(orders);
    }

    /**
     * @param id идентификатор заказа
     * @return заказ или {@code null} если не найден
     */
    public Order findById(int id) {
        return getAll().stream()
                .filter(o -> o.id == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param userId идентификатор пользователя
     * @return список заказов пользователя
     */
    public List<Order> findByUserId(int userId) {
        return getAll().stream()
                .filter(o -> o.user_id == userId)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * @param id идентификатор заказа для удаления
     */
    public void deleteById(int id) {
        List<Order> orders = getAll();
        orders.removeIf(o -> o.id == id);
        saveAll(orders);
    }

    /**
     * @param id        идентификатор заказа
     * @param userId    новый идентификатор пользователя
     * @param productId новый идентификатор товара
     * @param price     новая цена
     */
    public void update(int id, int userId, int productId, Double price) {
        List<Order> orders = getAll();
        for (Order order : orders) {
            if (order.id == id) {
                order.user_id = userId;
                order.product_id = productId;
                order.price = price;
                break;
            }
        }
        saveAll(orders);
    }
}
