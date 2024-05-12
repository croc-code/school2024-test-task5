package ru.croc.exception;

/**
 * Исключение, выбрасываемое в случае, когда полученные данные некорректны и дальнейшая работа невозможна.
 *
 */
public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String message) {
        super(message);
    }
}
