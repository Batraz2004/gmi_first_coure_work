package app;

import app.services.*;

public class App {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private CartService cartService;

    public void init() {
        this.productService = new ProductService();
        this.userService = new UserService();
        this.orderService = new OrderService();
        this.cartService = new CartService();
    }

    public ProductService getProductService() {
        return this.productService;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public OrderService getOrderService() {
        return this.orderService;
    }

    public CartService getCartService() {
        return this.cartService;
    }
}