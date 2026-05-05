package app.data.Models;

public class Order {
    public int id;
    public int user_id;
    public int product_id;
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
