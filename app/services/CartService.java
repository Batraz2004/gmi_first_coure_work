package app.services;

import app.data.Models.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class CartService implements serviceInterface<Cart> {
    private static final String FILE_PATH = "app/data/carts.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Читать всю корзину из файла
    public List<Cart> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Cart>>() {
            }.getType();
            List<Cart> cart = gson.fromJson(reader, listType);
            return cart != null ? cart : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Сохранить список в файл
    public void saveAll(List<Cart> cart) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cart, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Добавить товар в корзину
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

    // Получить корзину пользователя
    public List<Cart> findByUserId(int userId) {
        return getAll().stream()
                .filter(c -> c.user_id == userId)
                .collect(java.util.stream.Collectors.toList());
    }

    // Найти по id
    public Cart findById(int id) {
        return getAll().stream()
                .filter(c -> c.id == id)
                .findFirst()
                .orElse(null);
    }

    // Удалить товар из корзины по id
    public void deleteById(int id) {
        List<Cart> cart = getAll();
        cart.removeIf(c -> c.id == id);
        saveAll(cart);
    }

    // Очистить корзину пользователя
    public void clearByUserId(int userId) {
        List<Cart> cart = getAll();
        cart.removeIf(c -> c.user_id == userId);
        saveAll(cart);
    }

    // Посчитать итого по корзине пользователя
    public Double getTotalByUserId(int userId) {
        return findByUserId(userId).stream()
                .mapToDouble(c -> c.price * c.quantity)
                .sum();
    }

    // Редактировать количество товара
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