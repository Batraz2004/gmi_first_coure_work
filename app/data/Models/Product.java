package app.data.Models;

public class Product {
    public int id;
    public int user_id;
    public transient User user;
    public int quantity;
    public String name;
    // public Category category;
    public Double price;

    public Product(String name, int user_id, Double price, int quantity) {
        this.name = name;
        this.user_id = user_id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', second-name=" + user_id + "," + "password = "+price + "}";
    }

}
