package app;

import java.util.List;
import java.util.Scanner;

import app.Enums.RoleEnum;
import app.data.Models.User;
import app.services.UserService;

public class Auth {
    private Boolean isAuthorized;
    public User currentUser = null;

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

    public Boolean isAuthorized() {
        return this.isAuthorized;
    }

    public String getRole() {
        return this.currentUser.role;
    }

    public boolean isAdmin() {
        return this.currentUser.role.equalsIgnoreCase(RoleEnum.Admin.toString());
    }

    public boolean isSupplier() {
        return this.currentUser.role.equalsIgnoreCase(RoleEnum.Supplier.toString());
    }

    public void logout() {
        this.currentUser = null;
        this.isAuthorized = false;
    }

    @Override
    public String toString() {
        String result = String.format("Login: %s, Role: %s", this.currentUser.login, this.currentUser.role);
        return result;
    }
}
