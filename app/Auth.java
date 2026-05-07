package app;

import java.util.List;
import java.util.Scanner;

import app.Enums.RoleEnum;
import app.data.Models.User;
import app.services.UserService;

/**
 * Отвечает за авторизацию пользователя.
 * Хранит текущего пользователя и предоставляет методы проверки роли.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class Auth {
    private Boolean isAuthorized;
    public User currentUser = null;

    /**
     * Запрашивает логин и пароль из консоли и ищет совпадение в {@code users.json}.
     *
     * @return {@code true} если пользователь найден, {@code false} иначе
     */
    public Boolean login() {
        // ввод лоига и пароля
        Scanner in = new Scanner(System.in);
        System.out.println("ваш логин:");
        String login = in.nextLine();
        System.out.println("ваш пароль:");
        String password = in.nextLine();

        // поиск в файле и присвоение роли
        UserService userService = new UserService();
        List<User> users = userService.getAll();

        User founderUser = users.stream()
                .filter(u -> u.login.equals(login) && u.password.equals(password))
                .findFirst()
                .orElse(null);

        Boolean result = founderUser != null;

        if (result) {
            this.currentUser = founderUser;
            this.isAuthorized = true;
        }

        return result;
    }

    /**
     * @return {@code true} если пользователь авторизован
     */
    public Boolean isAuthorized() {
        return this.isAuthorized;
    }

    /**
     * @return роль текущего пользователя
     */
    public String getRole() {
        return this.currentUser.role;
    }

    /**
     * @return {@code true} если роль текущего пользователя — Admin
     */
    public boolean isAdmin() {
        return this.currentUser.role.equalsIgnoreCase(RoleEnum.Admin.toString());
    }

    /**
     * @return {@code true} если роль текущего пользователя — Supplier
     */
    public boolean isSupplier() {
        return this.currentUser.role.equalsIgnoreCase(RoleEnum.Supplier.toString());
    }

    /**
     * Сбрасывает текущего пользователя и статус авторизации.
     */
    public void logout() {
        this.currentUser = null;
        this.isAuthorized = false;
    }

    /**
     * @return строка вида {@code Login: ..., Role: ...}
     */
    @Override
    public String toString() {
        String result = String.format("Login: %s, Role: %s", this.currentUser.login, this.currentUser.role);
        return result;
    }
}
