package app.data.Models;

public class Product {
    public int id;
    public int supplier_id;
    public String name;
    // public Category category;
    public Double price;

    public Product(String name, int supplier_id, Double price) {
        this.name = name;
        this.supplier_id = supplier_id;
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
        return "Person{name='" + name + "', second-name=" + supplier_id + "," + "password = "+price + "}";
    }

}
