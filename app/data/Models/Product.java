package app.data.Models;

/**
 * Модель товара в магазине.
 * Поля {@code user} и {@code product} не сериализуются в JSON (transient),
 * и заполняются через {@code resolve()} в сервисе.
 *
 * @author Batraz2004
 * @version 1.0
 */
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
