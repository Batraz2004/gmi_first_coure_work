package app.data.Models;

public class Cart {

    public int id;
    public int user_id;
    public int product_id;
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