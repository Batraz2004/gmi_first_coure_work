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

public class OrderService implements baseService<Order> {
    private static final String FILE_PATH = "app/data/orders.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ProductService productService;
    private final UserService userService;

    public OrderService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public Order resolve(Order order) {
        order.product = productService.findById(order.product_id);
        order.user = userService.findById(order.user_id);
        return order;
    }

    // Читать все заказы из файла
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

    // Сохранить список в файл
    public void saveAll(List<Order> orders) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(orders, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Добавить заказ
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

    // Найти по id
    public Order findById(int id) {
        return getAll().stream()
                .filter(o -> o.id == id)
                .findFirst()
                .orElse(null);
    }

    // Найти по user_id
    public List<Order> findByUserId(int userId) {
        return getAll().stream()
                .filter(o -> o.user_id == userId)
                .collect(java.util.stream.Collectors.toList());
    }

    // Удалить по id
    public void deleteById(int id) {
        List<Order> orders = getAll();
        orders.removeIf(o -> o.id == id);
        saveAll(orders);
    }

    // Редактировать заказ
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