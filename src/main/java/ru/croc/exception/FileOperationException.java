package ru.croc.exception;

/**
 * Ошибка при работе с файлом
 */
public class FileOperationException extends RuntimeException {
    /**
     * Конструктор
     *
     * @param message сообщение ошибки
     * @param e       ошибка
     */
    public FileOperationException(String message, Exception e) {
        super(message, e);
    }
}
