package app;

import app.Enums.RoleEnum;
import app.data.Models.*;
import app.services.*;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final App app;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(App app) {
        this.app = app;
    }

    public void run() {
        while (true) {
            System.out.println("\n===== ГЛАВНОЕ МЕНЮ =====");
            System.out.println("1. Товары");
            System.out.println("2. Поставщики");
            System.out.println("3. Клиенты");
            System.out.println("4. Заказы");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    productMenu();
                    break;
                case "2":
                    supplierMenu();
                    break;
                case "3":
                    userMenu();
                    break;
                case "4":
                    orderMenu();
                    break;
                case "5":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }

    private void productMenu() {

    }

    private void supplierMenu() {

    }

    private void userMenu() {
        while (true) {
            System.out.println("\n===== Меню пользователей =====");
            System.out.println("1. список пользователей");
            System.out.println("2. удалить пользователя");
            System.out.println("3. редактировать пользователя");
            System.out.println("4. создать нового пользователя");
            System.out.println("5. массовая загрузка");
            System.out.println("6. Выйти из меню пользователей");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<User> users = app.getUserService().getAll();
                    for (User user : users) {
                        System.out.println("user_id:" + user.id + "; user_name:" + user.name);
                    }
                    break;
                case "2":
                    System.out.print("Введите id пользователя для удаления: ");
                    int deleteId = Integer.parseInt(this.scanner.nextLine());
                    app.getUserService().deleteById(deleteId);
                    System.out.println("Пользователь удалён.");
                    break;
                case "3":
                    System.out.print("Введите id пользователя для редактирования: ");
                    int editId = Integer.parseInt(this.scanner.nextLine());
                    System.out.print("Новое имя: ");
                    String newName = scanner.nextLine();
                    System.out.print("Новый пароль: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Новая роль (admin/moderator/user): ");
                    String newRole = this.roleChange();

                    app.getUserService().update(editId, newName, newPassword, newRole);
                    System.out.println("Пользователь обновлён.");
                    break;
                case "4":
                    System.out.print("Введите имя нового пользователя: ");
                    String name = this.scanner.nextLine();
                    System.out.print("Введите пароль:");
                    String password = this.scanner.nextLine();

                    User user = new User(name, password, "User");

                    app.getUserService().add(user);

                    break;
                case "5":
                    System.out.print("Сколько пользователей создать? ");
                    int count = Integer.parseInt(this.scanner.nextLine());

                    for (int i = 0; i < count; i++) {
                        System.out.println("\n--- Пользователь " + (i + 1) + " ---");
                        System.out.print("Имя: ");
                        String mName = this.scanner.nextLine();
                        System.out.print("Пароль: ");
                        String mPassword = this.scanner.nextLine();
                        System.out.print("Выберите роль: \n\r1:admin/\n\r2:supplier\n\r3:user");

                        String mRole = this.roleChange();

                        app.getUserService().add(new User(mName, mPassword, mRole));
                    }
                    System.out.println("Пользователи созданы.");

                    break;
                case "6":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }

    private void orderMenu() {

    }

    private String roleChange() {
        int roleChoice = this.scanner.nextInt();

        String role = "";

        switch (roleChoice) {
            case 1:
                role = RoleEnum.Admin.toString();
                break;
            case 2:
                role = RoleEnum.Supplier.toString();
                break;
            case 3:
                role = RoleEnum.User.toString();
                break;

            default:
                role = RoleEnum.User.toString();
                break;
        }

        return role;
    }
}