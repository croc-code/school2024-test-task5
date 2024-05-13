package ru.croc.utils.file;

import ru.croc.exception.FileOperationException;
import ru.croc.exception.InitializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс для записи имен самых важных стейкхолдеров в файл
 */
public class ResourcesWriter {
    private final Path PATH_TO_RESULT;

    /**
     * Конструктор
     *
     * @param pathToResult путь до файла с результатом
     * @throws InitializationException ошибка при переданном null параметре
     */
    public ResourcesWriter(String pathToResult) {
        if (pathToResult == null) {
            throw new InitializationException("Передан null путь");
        }
        PATH_TO_RESULT = Path.of(pathToResult);
    }

    /**
     * Записывает имена самых важных стейкхолдеров в файл
     *
     * @param mostImportantStakeholders строка с именами самых важных стейкхолдеров
     * @throws FileOperationException ошибка при записи в файл
     */
    public void writeMostImportantStakeholders(String mostImportantStakeholders) {
        createFileIfMissing();

        try {
            Files.writeString(PATH_TO_RESULT, mostImportantStakeholders);
        } catch (IOException e) {
            throw new FileOperationException("Произошла ошибка во время записи в файл по пути: " +
                    PATH_TO_RESULT, e);
        }
    }

    /**
     * Создаёт файл, если он отсутствует
     * @throws FileOperationException ошибка при попытке создать файл
     */
    private void createFileIfMissing() {
        if (!Files.exists(PATH_TO_RESULT)) {
            try {
                Files.createFile(PATH_TO_RESULT);
            } catch (IOException e) {
                throw new FileOperationException("Произошла ошибка во время создания файла по пути: " +
                        PATH_TO_RESULT, e);
            }
        }
    }
}
