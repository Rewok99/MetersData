package service;

import in.UserInput;
import model.MeterReading;
import model.User;
import model.UserAction;
import out.UserOutput;

import java.time.LocalDateTime;
import java.util.*;

/**

 * Класс, представляющий сервис учета показаний счетчиков и управления пользователями.
 */
public class MeterReadingService {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, List<MeterReading>> meterReadings = new HashMap<>();
    private static List<UserAction> userActions = new ArrayList<>();
    private static UserInput userInput = new UserInput();

    /**
     * Метод main, запускающий приложение и предоставляющий пример использования сервиса.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        // Пример использования
        registerUser("admin", "admin", true);

        String username = null;
        boolean exitRequested = false;

        while (!exitRequested) {
            UserOutput.printMessage("\nГлавное меню:");
            UserOutput.printMenu(new String[]{
                    "Войти",
                    "Зарегистрироваться",
                    "Выйти"
            });

            int choice = userInput.getIntInput();
            userInput.getStringInput();  // Считываем перевод строки

            switch (choice) {
                case 1:
                    username = authenticateUser();
                    if (username != null) {
                        UserOutput.printMessage("Авторизация успешна для пользователя " + username + ".");
                        if (users.get(username).isAdmin()) {
                            processAdminMenu();
                        } else {
                            processUserMenu(username);
                        }
                    } else {
                        UserOutput.printMessage("Неверное имя пользователя или пароль. Повторите ввод.");
                    }
                    break;
                case 2:
                    registerNewUser();
                    UserOutput.printMessage("Регистрация успешна. Теперь вы можете войти в свой аккаунт.");
                    break;
                case 3:
                    exitRequested = true;
                    logUserActions();
                    break;
                default:
                    UserOutput.printMessage("Некорректный выбор. Повторите ввод.");
            }
        }

        UserOutput.printMessage("Выход из приложения.");
    }

    /**
     * Обработка меню для администратора.
     */
    private static void processAdminMenu() {
        boolean exitRequested = false;

        while (!exitRequested) {
            UserOutput.printMessage("\nМеню администратора:");
            UserOutput.printMenu(new String[]{
                    "Просмотреть актуальные показания счетчиков для всех пользователей",
                    "Просмотреть историю показателей счетчиков для всех пользователей",
                    "Просмотреть показания за конкретный месяц",
                    "Выйти из режима администратора"
            });

            int choice = userInput.getIntInput();
            userInput.getStringInput();

            switch (choice) {
                case 1:
                    viewMeterReadingsForAllUsers();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    viewMeterReadingsForMonth();
                    break;
                case 4:
                    exitRequested = true;
                    userActions.add(new UserAction("admin", "Выход из режима администратора", LocalDateTime.now()));
                    logUserActions();
                    break;
                default:
                    UserOutput.printMessage("Некорректный выбор. Повторите ввод.");
            }
        }

        UserOutput.printMessage("Выход из режима администратора.");
    }

    /**
     * Просмотр показаний счетчиков за конкретный месяц.
     */
    private static void viewMeterReadingsForMonth() {
        try {
            UserOutput.printMessage("Введите месяц:");
            int month = userInput.getIntInput();
            UserOutput.printMessage("Введите год:");
            int year = userInput.getIntInput();

            for (Map.Entry<String, List<MeterReading>> entry : meterReadings.entrySet()) {
                String username = entry.getKey();
                List<MeterReading> readings = entry.getValue();

                for (MeterReading reading : readings) {
                    if (reading.getMonth() == month && reading.getYear() == year) {
                        userActions.add(new UserAction(username, "Просмотр данных за конкретный месяц", LocalDateTime.now()));
                        UserOutput.printMeterReading(username, month, year, reading);
                        break;
                    }
                }
            }
        } catch (InputMismatchException e) {
            UserOutput.printMessage("Некорректный ввод. Пожалуйста, введите числовое значение.");
            userInput.getStringInput();
        }
    }

    /**
     * Просмотр актуальных показаний счетчиков для всех пользователей.
     */
    private static void viewMeterReadingsForAllUsers() {
        for (Map.Entry<String, List<MeterReading>> entry : meterReadings.entrySet()) {
            String username = entry.getKey();
            MeterReading latestReading = getLatestMeterReading(username);
            userActions.add(new UserAction(username, "Просмотр всех актуальных данных", LocalDateTime.now()));
            UserOutput.printMeterReading(username, latestReading.getMonth(), latestReading.getYear(), latestReading);
        }
    }

    /**
     * Просмотр истории показаний для всех пользователей.
     */
    private static void viewAllUsers() {
        for (Map.Entry<String, List<MeterReading>> entry : meterReadings.entrySet()) {
            String username = entry.getKey();
            List<MeterReading> history = getMeterReadingHistory(username);
            userActions.add(new UserAction(username, "Просмотр истории пользователей", LocalDateTime.now()));
            UserOutput.printHistory(username, history);
        }
    }

    /**
     * Аутентификация пользователя.
     *
     * @return Имя пользователя, если аутентификация успешна; в противном случае null.
     */
    private static String authenticateUser() {
        try {
            UserOutput.printMessage("Введите имя пользователя (или 'exit' для выхода):");
            String username = userInput.getStringInput();

            if ("exit".equalsIgnoreCase(username)) {
                return null;
            }

            UserOutput.printMessage("Введите пароль:");
            String password = userInput.getStringInput();

            return authenticateUser(username, password) ? username : null;
        } catch (Exception e) {
            UserOutput.printMessage("Произошла ошибка при вводе данных.");
            return null;
        }
    }

    /**
     * Регистрация нового пользователя.
     */
    private static void registerNewUser() {
        try {
            UserOutput.printMessage("Введите новое имя пользователя:");
            String newUsername = userInput.getStringInput();

            if (users.containsKey(newUsername)) {
                UserOutput.printMessage("Пользователь с таким именем уже существует.");
                return;
            }

            UserOutput.printMessage("Введите пароль для нового пользователя:");
            String newPassword = userInput.getStringInput();

            registerUser(newUsername, newPassword, false);
        } catch (Exception e) {
            UserOutput.printMessage("Произошла ошибка при вводе данных.");
        }
    }

    /**
     * Обработка меню для пользователя.
     *
     * @param username Имя пользователя.
     */
    private static void processUserMenu(String username) {
        boolean exitRequested = false;

        while (!exitRequested) {
            UserOutput.printMessage("\nМеню для пользователя " + username + ":");
            UserOutput.printMenu(new String[]{
                    "Просмотреть актуальные показания счетчиков",
                    "Ввести новые показания счетчиков",
                    "Просмотреть историю показаний счетчиков",
                    "Просмотреть показания за конкретный месяц",
                    "Выйти из аккаунта",
                    "Выйти из приложения"
            });

            int choice = userInput.getIntInput();
            userInput.getStringInput();

            switch (choice) {
                case 1:
                    MeterReading latestReading = getLatestMeterReading(username);
                    UserOutput.printMessage("Актуальные данные счетчиков пользователя " + username + ": " + latestReading);
                    break;
                case 2:
                    MeterReading newReading = promptMeterReading();
                    submitMeterReading(username, newReading);
                    UserOutput.printMessage("Новые показания успешно внесены.");
                    break;
                case 3:
                    List<MeterReading> history = getMeterReadingHistory(username);
                    UserOutput.printHistory(username, history);
                    break;
                case 4:
                    viewMeterReadingsForMonth();
                    break;
                case 5:
                    exitRequested = true;
                    userActions.add(new UserAction(username, "Выход из аккаунта", LocalDateTime.now()));
                    break;
                case 6:
                    exitRequested = true;
                    userActions.add(new UserAction(username, "Выход из приложения", LocalDateTime.now()));
                    logUserActions();
                    break;
                default:
                    UserOutput.printMessage("Некорректный выбор. Повторите ввод.");
            }
        }

        UserOutput.printMessage("Выход из аккаунта " + username + ".");
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param isAdmin  Флаг, указывающий, является ли пользователь администратором.
     */
    public static void registerUser(String username, String password, boolean isAdmin) {
        User user = new User(username, password, isAdmin);
        users.put(username, user);
        userActions.add(new UserAction(username, "Регистрация пользователя", LocalDateTime.now()));
    }

    /**
     * Аутентификация пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return true, если аутентификация успешна; в противном случае false.
     */
    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Подача показаний счетчиков пользователем.
     *
     * @param username Имя пользователя.
     * @param reading  Показания счетчиков.
     */
    public static void submitMeterReading(String username, MeterReading reading) {
        meterReadings.computeIfAbsent(username, k -> new ArrayList<>()).add(reading);
        userActions.add(new UserAction(username, "Подача показаний", LocalDateTime.now()));
    }

    /**
     * Получение последних актуальных показаний счетчиков для пользователя.
     *
     * @param username Имя пользователя.
     * @return Последние актуальные показания счетчиков.
     */
    public static MeterReading getLatestMeterReading(String username) {
        List<MeterReading> readings = meterReadings.get(username);
        if (readings != null && !readings.isEmpty()) {
            return readings.get(readings.size() - 1);
        }
        userActions.add(new UserAction(username, "Просмотр актуальных данных", LocalDateTime.now()));
        return null;
    }

    /**
     * Получение истории показаний счетчиков для пользователя.
     *
     * @param username Имя пользователя.
     * @return История показаний счетчиков.
     */
    public static List<MeterReading> getMeterReadingHistory(String username) {
        userActions.add(new UserAction(username, "Просмотр истории", LocalDateTime.now()));
        return meterReadings.getOrDefault(username, Collections.emptyList());
    }

    /**
     * Журналирование действий пользователей.
     */
    private static void logUserActions() {
        UserOutput.printActions(userActions);
    }

    /**
     * Запрашивает у пользователя ввод данных для создания объекта MeterReading.
     *
     * @return Объект MeterReading, созданный на основе введенных данных пользователя.
     */
    private static MeterReading promptMeterReading() {
        UserOutput.printMessage("Введите месяц:");
        int month = userInput.getIntInput();
        UserOutput.printMessage("Введите год:");
        int year = userInput.getIntInput();
        UserOutput.printMessage("Введите показания отопления:");
        int heatingReading = userInput.getIntInput();
        UserOutput.printMessage("Введите показания горячей воды:");
        int hotWaterReading = userInput.getIntInput();
        UserOutput.printMessage("Введите показания холодной воды:");
        int coldWaterReading = userInput.getIntInput();

        return new MeterReading(month, year, heatingReading, hotWaterReading, coldWaterReading);
    }
}
