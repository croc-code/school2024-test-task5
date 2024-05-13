package ru.croc.exception;

/**
 * Ошибка при инициализации
 */
public class InitializationException extends RuntimeException {
    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     */
    public InitializationException(String message) {
        super(message);
    }
}
