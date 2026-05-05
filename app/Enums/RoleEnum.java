package app.Enums;

public enum RoleEnum {
    User,
    Supplier,
    Admin;

    public String label() {
        switch (this) {
            case Admin:
                return "Администратор";
            case Supplier:
                return "Поставщик";
            default:
                return "Пользователь";
        }
    }

    @Override
    public String toString() {
        return name();
    }
}