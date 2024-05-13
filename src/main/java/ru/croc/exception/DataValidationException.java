package ru.croc.exception;

/**
 * Ошибка валидации данных
 */
public class DataValidationException extends RuntimeException {
    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public DataValidationException(String message) {
        super(message);
    }
}
