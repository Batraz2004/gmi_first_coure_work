package app.services;

import app.Enums.SortEnum;
import app.data.Models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Сервис для работы с товарами.
 * Хранение данных — {@code app/data/products.json}.
 * Поддерживает сортировку по цене через {@link app.Enums.SortEnum}.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class ProductService implements baseService<Product> {
    private static final String FILE_PATH = "app/data/products.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private UserService userService;

    public ProductService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return список всех товаров, отсортированный по id
     */
    public List<Product> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();

            List<Product> products = gson.fromJson(reader, listType);

            if (products == null)
                return new ArrayList<>();

            products.forEach(this::resolve);
            products.sort((a, b) -> Integer.compare(a.id, b.id));

            return products;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Заполняет transient поле {@code user} из {@link UserService}.
     *
     * @param product товар
     * @return тот же товар с заполненной связью
     */
    public Product resolve(Product product) {
        product.user = userService.findById(product.user_id);
        return product;
    }

    /**
     * @param sort направление сортировки по цене
     * @return список товаров с применённой сортировкой
     */
    public List<Product> getAll(SortEnum sort) {
        List<Product> products = getAll();
        if (sort == SortEnum.Asc) {
            products.sort((a, b) -> Double.compare(a.price, b.price));
        } else if (sort == SortEnum.Desc) {
            products.sort((a, b) -> Double.compare(b.price, a.price));
        }
        return products;
    }

    /**
     * @param products список товаров для сохранения
     */
    public void saveAll(List<Product> products) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(products, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Автоматически присваивает id как maxId + 1.
     *
     * @param product новый товар
     */
    public void add(Product product) {
        List<Product> products = getAll();
        int maxId = products.stream()
                .mapToInt(p -> p.id)
                .max()
                .orElse(0);

        product.id = maxId + 1;

        products.add(product);
        saveAll(products);
    }

    /**
     * Поиск по вхождению строки, регистр игнорируется.
     *
     * @param query строка поиска
     * @return список товаров у которых название содержит {@code query}
     */
    public List<Product> searchByName(String query) {
        return getAll().stream()
                .filter(p -> p.name.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * @param id идентификатор товара
     * @return товар или {@code null} если не найден
     */
    public Product findById(int id) {
        return getAll().stream()
                .filter(p -> p.id == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param userId идентификатор поставщика
     * @return список товаров добавленных данным поставщиком
     */
    public List<Product> findByUserId(int userId) {
        return getAll().stream()
                .filter(p -> p.user_id == userId)
                .collect(Collectors.toList());
    }

    /**
     * @param id идентификатор товара для удаления
     */
    public void deleteById(int id) {
        List<Product> products = getAll();
        products.removeIf(p -> p.id == id);
        saveAll(products);
    }

    /**
     * @param id       идентификатор товара
     * @param name     новое название
     * @param price    новая цена
     * @param quantity новое количество
     */
    public void update(int id, String name, Double price, int quantity) {
        List<Product> products = getAll();
        for (Product product : products) {
            if (product.id == id) {
                product.name = name;
                product.price = price;
                product.quantity = quantity;
                break;
            }
        }
        saveAll(products);
    }
}
