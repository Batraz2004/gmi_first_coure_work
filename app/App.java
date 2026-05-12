package app;

import app.services.*;

/**
 * Точка инициализации приложения.
 * Создаёт и связывает все сервисы через dependency injection.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class App {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private CartService cartService;

    /**
     * Инициализирует все сервисы приложения.
     * Порядок важен — сервисы передаются друг другу через конструктор.
     */
    public void init() {
        this.userService = new UserService();
        this.productService = new ProductService(this.userService);
        this.orderService = new OrderService(this.productService, this.userService);
        this.cartService = new CartService(this.productService, this.userService);
    }

    /**
     * @return сервис товаров
     */
    public ProductService getProductService() {
        return this.productService;
    }

    /**
     * @return сервис пользователей
     */
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * @return сервис заказов
     */
    public OrderService getOrderService() {
        return this.orderService;
    }

    /**
     * @return сервис корзины
     */
    public CartService getCartService() {
        return this.cartService;
    }
}
