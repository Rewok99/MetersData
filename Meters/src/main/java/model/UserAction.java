package model;

import java.time.LocalDateTime;

/**
 * Класс, представляющий действие пользователя.
 */
public class UserAction {
    private String username;
    private String action;
    private LocalDateTime timestamp;

    /**
     * Конструктор для создания объекта UserAction.
     *
     * @param username Имя пользователя, выполнившего действие.
     * @param action   Описание выполняемого действия.
     * @param timestamp Временная метка действия.
     */
    public UserAction(String username, String action, LocalDateTime timestamp) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }

    /**
     * Возвращает имя пользователя, выполнившего действие.
     *
     * @return Имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает описание выполняемого действия.
     *
     * @return Описание действия.
     */
    public String getAction() {
        return action;
    }

    /**
     * Возвращает временную метку действия.
     *
     * @return Временная метка действия.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Переопределение метода toString для получения строкового представления объекта UserAction.
     *
     * @return Строковое представление объекта UserAction.
     */
    @Override
    public String toString() {
        return "UserAction{" +
                "username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
