package model;

/**
 * Класс, представляющий показания счетчиков.
 */
public class MeterReading {
    private int month;
    private int year;
    private int heatingReading;
    private int hotWaterReading;
    private int coldWaterReading;

    /**
     * Конструктор для создания объекта MeterReading.
     *
     * @param month             Месяц, к которому относится показание.
     * @param year              Год, к которому относится показание.
     * @param heatingReading   Показание счетчика отопления.
     * @param hotWaterReading  Показание счетчика горячей воды.
     * @param coldWaterReading Показание счетчика холодной воды.
     */
    public MeterReading(int month, int year, int heatingReading, int hotWaterReading, int coldWaterReading) {
        this.month = month;
        this.year = year;
        this.heatingReading = heatingReading;
        this.hotWaterReading = hotWaterReading;
        this.coldWaterReading = coldWaterReading;
    }

    /**
     * Получает месяц показания.
     *
     * @return Месяц показания.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Получает год показания.
     *
     * @return Год показания.
     */
    public int getYear() {
        return year;
    }

    /**
     * Получает показание счетчика отопления.
     *
     * @return Показание счетчика отопления.
     */
    public int getHeatingReading() {
        return heatingReading;
    }

    /**
     * Получает показание счетчика горячей воды.
     *
     * @return Показание счетчика горячей воды.
     */
    public int getHotWaterReading() {
        return hotWaterReading;
    }

    /**
     * Получает показание счетчика холодной воды.
     *
     * @return Показание счетчика холодной воды.
     */
    public int getColdWaterReading() {
        return coldWaterReading;
    }

    /**
     * Возвращает строковое представление объекта MeterReading.
     *
     * @return Строковое представление объекта MeterReading.
     */
    @Override
    public String toString() {
        return String.format("\nМесяц: %d, Год: %d, Отопление: %d, Горячая вода: %d, Холодная вода: %d",
                month, year, heatingReading, hotWaterReading, coldWaterReading);
    }
}
