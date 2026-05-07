package app.services;

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

public class ProductService {
    private static final String FILE_PATH = "app/data/products.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Читать все товары из файла
    public List<Product> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> products = gson.fromJson(reader, listType);
            return products != null ? products : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // С сортировкой
    public List<Product> getAll(String sort) {
        List<Product> products = getAll();
        if (sort.equals("asc")) {
            products.sort((a, b) -> Double.compare(a.price, b.price));
        } else if (sort.equals("desc")) {
            products.sort((a, b) -> Double.compare(b.price, a.price));
        }
        return products;
    }

    // Сохранить список в файл
    public void saveAll(List<Product> products) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(products, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Добавить товар
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

    // Найти по названию
    public List<Product> searchByName(String query) {
        return getAll().stream()
                .filter(p -> p.name.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Найти по id
    public Product findById(int id) {
        return getAll().stream()
                .filter(p -> p.id == id)
                .findFirst()
                .orElse(null);
    }

    // Найти по названию
    public Product findByName(String name) {
        return getAll().stream()
                .filter(p -> p.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    // Найти по user_id
    public List<Product> findByUserId(int userId) {
        return getAll().stream()
                .filter(p -> p.user_id == userId)
                .collect(Collectors.toList());
    }

    // Удалить по id
    public void deleteById(int id) {
        List<Product> products = getAll();
        products.removeIf(p -> p.id == id);
        saveAll(products);
    }

    // Редактировать товар
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