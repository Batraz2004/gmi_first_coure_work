package app;

import app.Enums.RoleEnum;
import app.Enums.SortEnum;
import app.data.Models.*;

import java.util.List;
import java.util.Scanner;

public class Controller {
    private final App app;
    private final Auth authUser;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * @param app      инициализированный экземпляр приложения со всеми сервисами
     * @param authUser авторизованный пользователь текущей сессии
     */
    public Controller(App app, Auth authUser) {
        this.app = app;
        this.authUser = authUser;
    }

    /**
     * Запускает главное меню приложения.
     * Работает в цикле до выбора пункта "Выход".
     * Доступ к разделу пользователей — только для Admin.
     */
    public void run() {
        if (authUser.isAuthorized()) {
            while (true) {
                try {
                    System.out.println("\n===== ГЛАВНОЕ МЕНЮ =====");
                    System.out.println("1. Товары");
                    System.out.println("2. Корзина");
                    System.out.println("3. Пользователи");
                    System.out.println("4. Заказы");
                    System.out.println("5. Выход");
                    System.out.print("Выберите действие: ");

                    int choice = Integer.parseInt(this.scanner.nextLine());

                    switch (choice) {
                        case 1:
                            this.productMenu();
                            break;
                        case 2:
                            this.cartMenu();
                            break;
                        case 3:
                            if (this.authUser.isAdmin()) {
                                this.userMenu();
                            } else {
                                System.out.println("нет доступа(permission denied)");
                            }
                            break;
                        case 4:
                            this.orderMenu();
                            break;
                        case 5:
                            this.authUser.logout();
                            System.out.println("До свидания!");
                            return;
                        default:
                            System.out.println("Неверный ввод.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число. " + e.getMessage());
                }
            }
        } else {
            System.out.println("Поробуйте еще раз авторизоваться");
            return;
        }
    }

    // ===== ТОВАРЫ =====
    private void productMenu() {
        int currentUserId = this.authUser.currentUser.id;

        while (true) {
            try {
                System.out.println("\n===== Меню товаров =====");
                System.out.println("1. Список товаров");
                System.out.println("2. Найти товар по id");
                System.out.println("3. Добавить товар");
                System.out.println("4. Редактировать товар");
                System.out.println("5. Удалить товар");
                System.out.println("6. Поиск по названию");
                System.out.println("7. Выйти");
                System.out.print("Выберите действие: ");

                int choice = Integer.parseInt(this.scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Сортировка по цене:");
                        System.out.println("1. По возрастанию");
                        System.out.println("2. По убыванию");
                        System.out.println("3. Без сортировки");

                        System.out.print("Выберите: ");
                        int sortChoice = Integer.parseInt(this.scanner.nextLine());

                        SortEnum sort;
                        switch (sortChoice) {
                            case 1:
                                sort = SortEnum.Asc;
                                break;
                            case 2:
                                sort = SortEnum.Desc;
                                break;
                            default:
                                sort = SortEnum.None;
                                break;
                        }

                        List<Product> products = app.getProductService().getAll(sort);
                        for (Product productItem : products) {
                            System.out.println(
                                    "id:" + productItem.id + " | name:" + productItem.name + " | price:"
                                            + productItem.price
                                            + " | quantity:" + productItem.quantity);
                        }

                        break;
                    case 2:
                        System.out.print("Введите id товара: ");
                        int findId = Integer.parseInt(scanner.nextLine());

                        Product foundProduct = app.getProductService().findById(findId);

                        if (foundProduct != null) {
                            System.out.println("id:" + foundProduct.id + " | name:" + foundProduct.name + " | price:"
                                    + foundProduct.price
                                    + " | quantity:" + foundProduct.quantity);
                        } else {
                            System.out.println("Товар не найден.");
                        }
                        break;
                    case 3:
                        if (this.authUser.isSupplier()) {
                            System.out.print("Название: ");
                            String productName = scanner.nextLine();

                            System.out.print("Цена: ");
                            Double productPrice = Double.parseDouble(scanner.nextLine().replace(",", "."));

                            System.out.print("Количество: ");
                            int productQuantity = Integer.parseInt(scanner.nextLine());

                            app.getProductService()
                                    .add(new Product(productName, currentUserId, productPrice, productQuantity));
                            System.out.println("Товар добавлен.");
                        } else {
                            System.out.println("Доступ запрещен(permission denied)");
                        }
                        break;
                    case 4:
                        if (this.authUser.isSupplier() || this.authUser.isAdmin()) {

                            System.out.print("Введите id товара: ");
                            int editItemId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Новое название: ");
                            String newName = scanner.nextLine();

                            System.out.print("Новая цена: ");
                            Double newPrice = Double.parseDouble(scanner.nextLine());

                            System.out.print("Новое количество: ");
                            int newQuantity = Integer.parseInt(scanner.nextLine());

                            int productUserId = this.app
                                    .getProductService()
                                    .findById(editItemId).user_id;

                            if (currentUserId != productUserId && !this.authUser.isAdmin()) {
                                System.out.println("У вас нет прав");
                                break;
                            }

                            app.getProductService().update(editItemId, newName, newPrice, newQuantity);
                            System.out.println("Товар обновлён.");
                        } else {
                            System.out.println("Доступ запрещен(permission denied)");
                        }
                        break;
                    case 5:
                        if (this.authUser.isSupplier() || this.authUser.isAdmin()) {
                            System.out.print("Введите id товара для удаления: ");
                            int delId = Integer.parseInt(scanner.nextLine());

                            int productId = this.app
                                    .getProductService()
                                    .findById(delId).id;

                            int productUserId = this.app
                                    .getProductService()
                                    .findById(productId).user_id;

                            if (currentUserId != productUserId && !this.authUser.isAdmin()) {
                                System.out.println("У вас нет прав");
                                break;
                            }

                            app.getProductService().deleteById(delId);
                            System.out.println("Товар удалён.");
                        } else {
                            System.out.println("Доступ запрещен(permission denied)");
                        }
                        break;
                    case 6:
                        System.out.print("Введите название товара: ");
                        String searchName = scanner.nextLine();

                        products = app.getProductService().searchByName(searchName);

                        if (!products.isEmpty()) {
                            for (Product productItem : products) {
                                System.out.println("id:" + productItem.id + " | name:" + productItem.name + " | price:"
                                        + productItem.price
                                        + " | quantity:" + productItem.quantity);
                            }
                        } else {
                            System.out.println("Товары не найден.");
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Неверный ввод.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число. " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Ошибка: запись не найдена. " + e.getMessage());
            }
        }
    }

    // ===== КОРЗИНА =====
    private void cartMenu() {
        int currentUserId = this.authUser.currentUser.id;
        while (true) {
            try {
                System.out.println("\n===== Меню корзины =====");
                System.out.println("1. Посмотреть корзину пользователя");
                System.out.println("2. Добавить товар в корзину");
                System.out.println("3. Удалить товар из корзины");
                System.out.println("4. Очистить корзину пользователя");
                System.out.println("5. Итого по корзине");
                System.out.println("6. Выйти");
                System.out.print("Выберите действие: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        List<Cart> cartItems;
                        if (this.authUser.isAdmin()) {
                            cartItems = app.getCartService().getAll();
                        } else {
                            cartItems = app.getCartService().findByUserId(currentUserId);
                        }

                        if (cartItems.isEmpty()) {
                            System.out.println("Корзина пуста");
                        } else {
                            for (Cart cartItem : cartItems) {
                                String productName = cartItem.product != null ? cartItem.product.name
                                        : "удалён(id:" + cartItem.product_id + ")";
                                String userLogin = cartItem.user != null ? cartItem.user.login
                                        : "id:" + cartItem.user_id;
                                System.out.println("id:" + cartItem.id + " | user:" + userLogin + " | product:"
                                        + productName + " | quantity:" + cartItem.quantity + " | price:"
                                        + cartItem.price);
                            }
                        }
                        break;
                    case 2:
                        int targetUserIdAdd = currentUserId;
                        if (this.authUser.isAdmin()) {
                            System.out.print("ID пользователя: ");
                            targetUserIdAdd = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.print("ID товара: ");
                        int productId = Integer.parseInt(scanner.nextLine());
                        Product productToAdd = this.app.getProductService().findById(productId);

                        if (productToAdd == null) {
                            System.out.println("Товар не найден");
                            break;
                        }

                        System.out.print("Количество: ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        app.getCartService().add(new Cart(targetUserIdAdd, productId, quantity, productToAdd.price));

                        System.out.println("Товар добавлен в корзину.");
                        break;
                    case 3:
                        int targetUserIdDel = currentUserId;
                        if (this.authUser.isAdmin()) {
                            System.out.print("ID пользователя: ");
                            targetUserIdDel = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.print("Введите id записи в корзине: ");
                        int cartEntryId = Integer.parseInt(scanner.nextLine());

                        Cart cartItem = this.app.getCartService().findById(cartEntryId);

                        if (cartItem == null) {
                            System.out.println("Запись не найдена");
                            break;
                        } else if (cartItem.user_id != targetUserIdDel) {
                            System.out.println("У вас нет прав.");
                            break;
                        }

                        app.getCartService().deleteById(cartItem.id);
                        System.out.println("Товар удалён из корзины.");
                        break;
                    case 4:
                        int targetUserIdClear = currentUserId;
                        if (this.authUser.isAdmin()) {
                            System.out.print("ID пользователя: ");
                            targetUserIdClear = Integer.parseInt(scanner.nextLine());
                        }

                        app.getCartService().clearByUserId(targetUserIdClear);
                        System.out.println("Корзина очищена.");
                        break;
                    case 5:
                        Double total = app.getCartService().getTotalByUserId(currentUserId);
                        System.out.println("Итого: " + total);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Неверный ввод.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число. " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Ошибка: запись не найдена. " + e.getMessage());
            }
        }
    }

    private void userMenu() {
        while (true) {
            try {
                System.out.println("\n===== Меню пользователей =====");
                System.out.println("1. список пользователей");
                System.out.println("2. удалить пользователя");
                System.out.println("3. редактировать пользователя");
                System.out.println("4. создать нового пользователя");
                System.out.println("5. массовая загрузка");
                System.out.println("6. Выйти из меню пользователей");
                System.out.print("Выберите действие: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        List<User> users = app.getUserService().getAll();

                        for (User user : users) {
                            System.out.println("user_id:" + user.id + "; login:" + user.login);
                        }

                        break;
                    case 2:
                        System.out.print("Введите id пользователя для удаления: ");
                        int deleteId = Integer.parseInt(this.scanner.nextLine());

                        app.getUserService().deleteById(deleteId);
                        System.out.println("Пользователь удалён.");

                        break;
                    case 3:
                        System.out.print("Введите id пользователя для редактирования: ");
                        int editId = Integer.parseInt(this.scanner.nextLine());

                        System.out.print("Новый логин: ");
                        String newName = scanner.nextLine();

                        System.out.print("Новый пароль: ");
                        String newPassword = scanner.nextLine();

                        String newRole = this.roleChange();

                        app.getUserService().update(editId, newName, newPassword, newRole);
                        System.out.println("Пользователь обновлён.");

                        break;
                    case 4:
                        System.out.print("Введите логин нового пользователя: ");
                        String name = this.scanner.nextLine();

                        System.out.print("Введите пароль:");
                        String password = this.scanner.nextLine();
                        String role = this.roleChange();
                        
                        User user = new User(name, password, role);

                        app.getUserService().add(user);
                        System.out.println("Пользователь создан.");

                        break;
                    case 5:
                        System.out.print("Сколько пользователей создать? ");
                        int count = Integer.parseInt(this.scanner.nextLine());

                        for (int i = 0; i < count; i++) {
                            System.out.println("\n--- Пользователь " + (i + 1) + " ---");

                            System.out.print("Логин: ");
                            String newUserName = this.scanner.nextLine();

                            System.out.print("Пароль: ");
                            String newUserPassword = this.scanner.nextLine();

                            String newUserRole = this.roleChange();

                            app.getUserService().add(new User(newUserName, newUserPassword, newUserRole));
                        }
                        System.out.println("Пользователи созданы.");

                        break;
                    case 6:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный ввод.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число. " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Ошибка: запись не найдена. " + e.getMessage());
            }
        }
    }

    // ===== ЗАКАЗЫ =====
    private void orderMenu() {
        int currentUserId = this.authUser.currentUser.id;

        while (true) {
            try {
                System.out.println("\n===== Меню заказов =====");
                System.out.println("1. Список всех заказов");
                System.out.println("2. Заказы пользователя");
                System.out.println("3. Создать заказ");
                System.out.println("4. Удалить заказ");
                System.out.println("5. Выйти");
                System.out.print("Выберите действие: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        List<Order> orders;

                        if (this.authUser.isAdmin()) {
                            orders = app.getOrderService().getAll();
                        } else {
                            orders = app.getOrderService().findByUserId(currentUserId);

                        }

                        if (orders.isEmpty()) {
                            System.out.println(this.authUser.isAdmin() ? "Заказов нет." : "У вас нет заказов.");
                            break;
                        }

                        for (Order orderItem : orders) {
                            String productName = orderItem.product != null ? orderItem.product.name
                                    : "удалён(id:" + orderItem.product_id + ")";

                            String userLogin = orderItem.user != null ? orderItem.user.login
                                    : "id:" + orderItem.user_id;

                            System.out.println("id:" + orderItem.id + " | user:" + userLogin + " | product:"
                                    + productName + " | price:" + orderItem.price);
                        }
                        break;
                    case 2:
                        orders = app.getOrderService().findByUserId(currentUserId);

                        for (Order orderItem : orders) {
                            String productName = orderItem.product != null ? orderItem.product.name
                                    : "удалён(id:" + orderItem.product_id + ")";
                            System.out.println(
                                    "id:" + orderItem.id + " | product:" + productName + " | price:" + orderItem.price);
                        }
                        break;
                    case 3:
                        int targetUserId;
                        if (this.authUser.isAdmin()) {
                            System.out.print("ID пользователя: ");
                            targetUserId = Integer.parseInt(scanner.nextLine());
                        } else {
                            targetUserId = currentUserId;
                        }

                        System.out.print("ID товара из корзины: ");
                        int cartItemIdInFind = Integer.parseInt(scanner.nextLine());

                        Cart cartItem = this.app.getCartService().findById(cartItemIdInFind);

                        if (cartItem == null) {
                            System.out.print("Товар не найден: ");
                            break;
                        } else if ((cartItem.user_id != currentUserId) && !this.authUser.isAdmin()) {
                            System.out.println("У вас нет прав.");
                            break;
                        }

                        Double orderItemPrice = cartItem.price * cartItem.quantity;

                        app.getOrderService().add(new Order(targetUserId, cartItemIdInFind, orderItemPrice));
                        System.out.println("Заказ создан.");
                        break;
                    case 4:
                        System.out.print("Введите id заказа для удаления: ");
                        int deleteItemFindId = Integer.parseInt(scanner.nextLine());

                        Order orderItem = this.app.getOrderService().findById(deleteItemFindId);

                        if (orderItem == null) {
                            System.out.print("Заказ не найден: ");
                            break;
                        } else if ((orderItem.user_id != currentUserId) || !this.authUser.isAdmin()) {
                            System.out.println("У вас нет прав.");
                            break;
                        }

                        app.getOrderService().deleteById(deleteItemFindId);
                        System.out.println("Заказ удалён.");
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Неверный ввод.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число. " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Ошибка: запись не найдена. " + e.getMessage());
            }
        }
    }

    private String roleChange() {
        System.out.print("Веберите роль (\n1:admin\n2:supplier\n3:user): ");

        int roleChoice = Integer.parseInt(this.scanner.nextLine());

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