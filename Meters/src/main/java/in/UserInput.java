package in;

import java.util.Scanner;

/**
 * Класс для ввода данных от пользователя.
 */
public class UserInput {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Считывает целочисленное значение из стандартного ввода.
     *
     * @return Введенное целочисленное значение.
     */
    public static int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите числовое значение.");
        }
        return 0;
    }

    /**
     * Считывает строковое значение из стандартного ввода.
     *
     * @return Введенная строка.
     */
    public static String getStringInput() {
        return scanner.nextLine();
    }
}

