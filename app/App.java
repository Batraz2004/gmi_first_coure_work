package app;

import app.services.*;

public class App {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private CartService cartService;

    public void init() {
        this.userService = new UserService();
        this.productService = new ProductService(this.userService);
        this.orderService = new OrderService(this.productService, this.userService);
        this.cartService = new CartService(this.productService, this.userService);
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