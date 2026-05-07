package app;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import app.Enums.RoleEnum;
import app.data.Models.User;
import app.services.UserService;

public class Auth {
    private static final String FILE_PATH = "app/data/users.json";

    private String role;
    private Boolean isAuthorized;
    private String name;
    private String password;

    public Boolean login() {
        // ввод лоига и пароля
        Scanner in = new Scanner(System.in);
        System.out.println("ваше имя:");
        String name = in.nextLine();
        System.out.println("ваш пароль:");
        String password = in.nextLine();

        // поиск в файле и присвоение роли
        UserService userService = new UserService();
        List<User> users = userService.getAll();

        User founderUser = users.stream()
                .filter(u -> u.name.equals(name) && u.password.equals(password))
                .findFirst()
                .orElse(null);

        Boolean result = founderUser != null;

        if (result) {
            this.name = founderUser.name;
            this.password = founderUser.password;
            this.role = founderUser.role;
            this.isAuthorized = true;
        }

        return result;
    }

    public Boolean isAuthorized() {
        return this.isAuthorized;
    }

    @Override
    public String toString() {
        String result = String.format("Name: %s, Role: %s", this.name, this.role);
        return result;
    }
}
