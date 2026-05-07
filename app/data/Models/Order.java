package app.data.Models;

/**
 * Модель заказа пользователя.
 * Поля {@code user} и {@code product} не сериализуются в JSON (transient),
 * и заполняются через {@code resolve()} в сервисе.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class Order {
    public int id;
    public int user_id;
    public transient User user;
    public int product_id;
    public transient Product product;
    public Double price;

    public Order(int user_id, int product_id, Double price) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "user_id:" + this.user_id + "; product_id:" + this.product_id + "; price:" + this.price;
    }
}
