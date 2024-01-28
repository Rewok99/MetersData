package model;

import java.util.Objects;

/**
 * Класс, представляющий пользователя системы.
 */
public class User {
    private String username;
    private String password;
    private boolean isAdmin;

    /**
     * Конструктор для создания объекта пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param isAdmin  Флаг, указывающий, является ли пользователь администратором.
     */
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Получает имя пользователя.
     *
     * @return Имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Получает пароль пользователя.
     *
     * @return Пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @return true, если пользователь администратор, в противном случае - false.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Переопределение метода equals для сравнения объектов User.
     *
     * @param o Объект для сравнения.
     * @return true, если объекты равны, в противном случае - false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    /**
     * Переопределение метода hashCode для вычисления хеш-кода объекта User.
     *
     * @return Хеш-код объекта User.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, isAdmin);
    }
}
