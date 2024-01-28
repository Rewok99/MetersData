package out;

import model.MeterReading;
import model.UserAction;

import java.util.List;

/**
 * Класс для вывода информации пользователю.
 */
public class UserOutput {

    /**
     * Выводит меню на экран.
     *
     * @param menuItems Массив строк, представляющих пункты меню.
     */
    public static void printMenu(String[] menuItems) {
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + ". " + menuItems[i]);
        }
    }

    /**
     * Выводит текстовое сообщение на экран.
     *
     * @param message Сообщение для вывода.
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Выводит показания счетчиков на экран.
     *
     * @param username Имя пользователя.
     * @param month    Месяц.
     * @param year     Год.
     * @param reading  Объект MeterReading, представляющий показания счетчиков.
     */
    public static void printMeterReading(String username, int month, int year, MeterReading reading) {
        System.out.println("Показания счетчиков для пользователя " + username + " за " +
                month + "/" + year + ": " + reading);
    }

    /**
     * Выводит историю показаний счетчиков на экран.
     *
     * @param username Имя пользователя.
     * @param history  Список MeterReading, представляющий историю показаний.
     */
    public static void printHistory(String username, List<MeterReading> history) {
        System.out.println("История показаний для пользователя " + username + ": " + history);
    }

    /**
     * Выводит журнал действий пользователей на экран.
     *
     * @param userActions Список UserAction, представляющий действия пользователей.
     */
    public static void printActions(List<UserAction> userActions) {
        System.out.println("\nЖурнал действий пользователей:");

        for (UserAction action : userActions) {
            System.out.println(action);
        }
    }
}
