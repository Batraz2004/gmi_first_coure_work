package app.data.Models;

public class User {
    public int id;
    public String name;
    public String role;
    public String password;

    public User(String name, String password, String role) {
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name:" + this.getName() + "; password:";
    }
}
