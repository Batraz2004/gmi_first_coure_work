package app;

import java.util.ArrayList;

import app.data.Models.Order;
import app.data.Models.Product;
import app.data.Models.Supplier;
import app.data.Models.User;
import app.services.*;

public class App {
    // private ProductService productService;
    private UserService userService;
    // private SupplierService supplierService;
    // private OrderService orderService;

    public void init() {
        // DataService dataService = new DataService();

        // ArrayList<Product> products = dataService.loadFromFile("products.json", Product.class);
        // ArrayList<User> users = dataService.loadFromFile("users.json", User.class);
        // ArrayList<Supplier> suppliers = dataService.loadFromFile("suppliers.json", Supplier.class);
        // ArrayList<Order> orders = dataService.loadFromFile("orders.json", Order.class);

        // this.productService = new ProductService();
        this.userService = new UserService();
        // this.supplierService = new SupplierService();
        // this.orderService = new OrderService();
    }

    // public ProductService getProductService() {
    //     return productService;
    // }

    public UserService getUserService() {
        return userService;
    }

    // public SupplierService getSupplierService() {
    //     return supplierService;
    // }

    // public OrderService getOrderService() {
    //     return orderService;
    // }
}