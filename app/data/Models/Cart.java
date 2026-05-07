package app.data.Models;

/**
 * Модель элемента корзины пользователя.
 * Поля {@code user} и {@code product} не сериализуются в JSON (transient),
 * и заполняются через {@code resolve()} в сервисе.
 *
 * @author Batraz2004
 * @version 1.0
 */
public class Cart {

    public int id;
    public int user_id;
    public transient User user;
    public int product_id;
    public transient Product product;
    public int quantity;
    public Double price;

    public Cart(int user_id, int product_id, int quantity, Double price) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "user_id:" + user_id + "; product_id:" + product_id + "; quantity:" + quantity + "; price:" + price;
    }
}