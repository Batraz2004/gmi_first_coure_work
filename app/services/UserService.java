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

/**
 * Сервис для работы с пользователями.
 * Хранение данных — {@code app/data/users.json}.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class UserService implements baseService<User> {
    private static final String FILE_PATH = "app/data/users.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * @return список всех пользователей или пустой список при ошибке
     */
    public List<User> getAll() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * @param users список пользователей для сохранения
     */
    public void saveAll(List<User> users) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Автоматически присваивает id как maxId + 1.
     *
     * @param user новый пользователь
     */
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

    /**
     * @param id идентификатор пользователя
     * @return пользователь или {@code null} если не найден
     */
    public User findById(int id) {
        return getAll().stream()
                .filter(u -> u.id == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param login логин пользователя
     * @return пользователь или {@code null} если не найден
     */
    public User findByLogin(String login) {
        return getAll().stream()
                .filter(u -> u.login.equals(login))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param id идентификатор пользователя для удаления
     */
    public void deleteById(int id) {
        List<User> users = getAll();
        users.removeIf(u -> u.id == id);
        saveAll(users);
    }

    /**
     * @param id       идентификатор пользователя
     * @param login    новый логин
     * @param password новый пароль
     * @param role     новая роль
     */
    public void update(int id, String login, String password, String role) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.id == id) {
                user.login = login;
                user.password = password;
                user.role = role;
                break;
            }
        }
        saveAll(users);
    }
}
