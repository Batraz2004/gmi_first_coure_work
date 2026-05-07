package app;

import app.Enums.RoleEnum;
import app.data.Models.*;

import java.util.List;
import java.util.Scanner;

public class Controller {
    private final App app;
    private final Scanner scanner = new Scanner(System.in);

    public Controller(App app) {
        this.app = app;
    }

    public void run() {
        while (true) {
            System.out.println("\n===== ГЛАВНОЕ МЕНЮ =====");
            System.out.println("1. Товары");
            System.out.println("2. Корзина");
            System.out.println("3. Пользователи");
            System.out.println("4. Заказы");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    productMenu();
                    break;
                case "2":
                    cartMenu();
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

    // ===== ТОВАРЫ =====
    private void productMenu() {
        while (true) {
            System.out.println("\n===== Меню товаров =====");
            System.out.println("1. Список товаров");
            System.out.println("2. Найти товар по id");
            System.out.println("3. Добавить товар");
            System.out.println("4. Редактировать товар");
            System.out.println("5. Удалить товар");
            System.out.println("6. Поиск по названию");
            System.out.println("7. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Сортировка по цене:");
                    System.out.println("1. По возрастанию");
                    System.out.println("2. По убыванию");
                    System.out.println("3. Без сортировки");
                    System.out.print("Выберите: ");
                    int sortChoice = scanner.nextInt();

                    String sort;
                    switch (sortChoice) {
                        case 1:
                            sort = "asc";
                            break;
                        case 2:
                            sort = "desc";
                            break;
                        default:
                            sort = "none";
                            break;
                    }

                    List<Product> products = app.getProductService().getAll(sort);
                    for (Product p : products) {
                        System.out.println(
                                "id:" + p.id + " | name:" + p.name + " | price:" + p.price + " | qty:" + p.quantity);
                    }
                    break;
                case "2":
                    System.out.print("Введите id товара: ");
                    int findId = Integer.parseInt(scanner.nextLine());
                    Product found = app.getProductService().findById(findId);
                    if (found != null) {
                        System.out.println("id:" + found.id + " | name:" + found.name + " | price:" + found.price
                                + " | qty:" + found.quantity);
                    } else {
                        System.out.println("Товар не найден.");
                    }
                    break;
                case "3":
                    System.out.print("Название: ");
                    String pName = scanner.nextLine();
                    System.out.print("Цена: ");
                    Double pPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Количество: ");
                    int pQty = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID пользователя: ");
                    int pUserId = Integer.parseInt(scanner.nextLine());
                    app.getProductService().add(new Product(pName, pUserId, pPrice, pQty));
                    System.out.println("Товар добавлен.");
                    break;
                case "4":
                    System.out.print("Введите id товара: ");
                    int editId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Новое название: ");
                    String newName = scanner.nextLine();
                    System.out.print("Новая цена: ");
                    Double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Новое количество: ");
                    int newQty = Integer.parseInt(scanner.nextLine());
                    app.getProductService().update(editId, newName, newPrice, newQty);
                    System.out.println("Товар обновлён.");
                    break;
                case "5":
                    System.out.print("Введите id товара для удаления: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    app.getProductService().deleteById(delId);
                    System.out.println("Товар удалён.");
                    break;
                case "6":
                    System.out.print("Введите название товара: ");
                    String searchName = scanner.nextLine();
                    Product result = app.getProductService().findByName(searchName);
                    if (result != null) {
                        System.out.println("id:" + result.id + " | name:" + result.name + " | price:" + result.price
                                + " | qty:" + result.quantity);
                    } else {
                        System.out.println("Товар не найден.");
                    }
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }

    // ===== КОРЗИНА =====
    private void cartMenu() {
        while (true) {
            System.out.println("\n===== Меню корзины =====");
            System.out.println("1. Посмотреть корзину пользователя");
            System.out.println("2. Добавить товар в корзину");
            System.out.println("3. Удалить товар из корзины");
            System.out.println("4. Очистить корзину пользователя");
            System.out.println("5. Итого по корзине");
            System.out.println("6. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Введите id пользователя: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    List<Cart> items = app.getCartService().findByUserId(userId);
                    for (Cart c : items) {
                        System.out.println("id:" + c.id + " | product_id:" + c.product_id + " | qty:" + c.quantity
                                + " | price:" + c.price);
                    }
                    break;
                case "2":
                    System.out.print("ID пользователя: ");
                    int cUserId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID товара: ");
                    int cProductId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Количество: ");
                    int cQty = Integer.parseInt(scanner.nextLine());
                    System.out.print("Цена: ");
                    Double cPrice = Double.parseDouble(scanner.nextLine());
                    app.getCartService().add(new Cart(cUserId, cProductId, cQty, cPrice));
                    System.out.println("Товар добавлен в корзину.");
                    break;
                case "3":
                    System.out.print("Введите id записи в корзине: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    app.getCartService().deleteById(delId);
                    System.out.println("Товар удалён из корзины.");
                    break;
                case "4":
                    System.out.print("Введите id пользователя для очистки корзины: ");
                    int clearId = Integer.parseInt(scanner.nextLine());
                    app.getCartService().clearByUserId(clearId);
                    System.out.println("Корзина очищена.");
                    break;
                case "5":
                    System.out.print("Введите id пользователя: ");
                    int totalId = Integer.parseInt(scanner.nextLine());
                    Double total = app.getCartService().getTotalByUserId(totalId);
                    System.out.println("Итого: " + total);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
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

    // ===== ЗАКАЗЫ =====
    private void orderMenu() {
        while (true) {
            System.out.println("\n===== Меню заказов =====");
            System.out.println("1. Список всех заказов");
            System.out.println("2. Заказы пользователя");
            System.out.println("3. Создать заказ");
            System.out.println("4. Удалить заказ");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Order> orders = app.getOrderService().getAll();
                    for (Order o : orders) {
                        System.out.println("id:" + o.id + " | user_id:" + o.user_id + " | product_id:" + o.product_id
                                + " | price:" + o.price);
                    }
                    break;
                case "2":
                    System.out.print("Введите id пользователя: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    List<Order> userOrders = app.getOrderService().findByUserId(userId);
                    for (Order o : userOrders) {
                        System.out.println("id:" + o.id + " | product_id:" + o.product_id + " | price:" + o.price);
                    }
                    break;
                case "3":
                    System.out.print("ID пользователя: ");
                    int oUserId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID товара: ");
                    int oProductId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Цена: ");
                    Double oPrice = Double.parseDouble(scanner.nextLine());
                    app.getOrderService().add(new Order(oUserId, oProductId, oPrice));
                    System.out.println("Заказ создан.");
                    break;
                case "4":
                    System.out.print("Введите id заказа для удаления: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    app.getOrderService().deleteById(delId);
                    System.out.println("Заказ удалён.");
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
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