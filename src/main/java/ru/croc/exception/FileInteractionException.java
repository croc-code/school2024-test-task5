package ru.croc.exception;

/**
 * Исключение, выбрасываемое в случае, если при работе с файлом произошла ошибка.
 * В сообщении содержит информацию о том, при работе с каким файлом произошла ошибка.
 */
public class FileInteractionException extends RuntimeException {
    public FileInteractionException(String message, Throwable e) {
        super(message, e);
    }
}
