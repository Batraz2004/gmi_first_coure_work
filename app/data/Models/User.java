package app.data.Models;

public class User {
    public int id;
    public String login;
    public String role;
    public String password;

    public User(String login, String password, String role) {
        this.login = login;
        this.role = role;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "login:" + this.getLogin() + "; password:";
    }
}
