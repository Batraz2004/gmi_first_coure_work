package app.services;

import app.data.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class UserService implements baseService<User> {
    private static final String FILE_PATH = "app/data/users.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Читать всех пользователей из файла
    public List<User> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Сохранить список в файл
    public void saveAll(List<User> users) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Добавить пользователя
    public void add(User user) {
        List<User> users = getAll();

        int maxId = users.stream()
                .mapToInt(p -> p.id)
                .max()
                .orElse(0);

        user.id = maxId + 1;

        users.add(user);
        saveAll(users);
    }

    // Найти по имени
    public User findByName(String name) {
        return getAll().stream()
                .filter(u -> u.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    // Удалить по id
    public void deleteById(int id) {
        List<User> users = getAll();
        users.removeIf(u -> u.id == id);
        saveAll(users);
    }

    public void update(int id, String name, String password, String role) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.id == id) {
                user.name = name;
                user.password = password;
                user.role = role;
                break;
            }
        }
        saveAll(users);
    }
}