package app.Enums;

public enum RoleEnum {
    User,
    Supplier,
    Admin;

    @Override
    public String toString() {
        return name();
    }
}