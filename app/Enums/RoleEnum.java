package app.Enums;

/**
 * Роли пользователей в системе.
 *
 * @author Batraz2004
 * @version 1.0
 */
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