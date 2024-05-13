package ru.croc.utils.file;

import lombok.SneakyThrows;
import ru.croc.data.Importance;
import ru.croc.exception.DataValidationException;
import ru.croc.exception.FileOperationException;
import ru.croc.exception.InitializationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс для считывания данных о стейкхолдерах из ресурсов
 */
public class ResourcesReader {
    private static final String MATRIX_DIAGONAL_DELIMITER = "_";
    private final String PATH_TO_INFLUENCE;
    private final String PATH_TO_INTEREST;

    /**
     * Конструктор.
     *
     * @param pathToInfluence путь до файла с матрицей влияния
     * @param pathToInterest  путь до файла с матрицей интереса
     * @throws InitializationException ошибка при переданном null параметре
     */
    public ResourcesReader(String pathToInfluence, String pathToInterest) {
        if (pathToInfluence == null || pathToInterest == null) {
            throw new InitializationException("Передан null путь");
        }
        PATH_TO_INFLUENCE = pathToInfluence;
        PATH_TO_INTEREST = pathToInterest;
    }

    /**
     * Формирует мапу с представлением (имя стейкхолдера - его важность)
     *
     * @return мапа с представлением (имя стейкхолдера - его важность)
     */
    public Map<String, Importance> getStakeholderDataMap() {
        Map<String, Importance> stakeholderDataMap = new HashMap<>();
        readInfluence(stakeholderDataMap);
        readInterest(stakeholderDataMap);
        return stakeholderDataMap;
    }

    /**
     * Читает файл с матрицей влияния
     *
     * @param stakeholderDataMap мапа с представлением (имя стейкхолдера - его важность)
     */
    private void readInfluence(Map<String, Importance> stakeholderDataMap) {
        String[] stakeholders;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_INFLUENCE))) {
            stakeholders = prepareStakeholders(reader, stakeholderDataMap);

            for (String stakeholder : stakeholders) {
                double influence = calculateArraySum(reader.readLine().split(" "));
                stakeholderDataMap.get(stakeholder).setInfluence(influence);
            }
        } catch (IOException e) {
            throw new FileOperationException("Произошла ошибка при чтении файла по пути: " +
                    PATH_TO_INFLUENCE, e);
        }
    }

    /**
     * Читает файл с матрицей интереса
     *
     * @param stakeholderDataMap мапа с представлением (имя стейкхолдера - его важность)
     */
    private void readInterest(Map<String, Importance> stakeholderDataMap) {
        String[] stakeholders;

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_INTEREST))) {
            stakeholders = prepareStakeholders(reader, stakeholderDataMap);

            for (String stakeholder : stakeholders) {
                double interest = calculateArraySum(reader.readLine().split(" "));
                stakeholderDataMap.get(stakeholder).setInterest(interest);
            }
        } catch (IOException e) {
            throw new FileOperationException("Произошла ошибка при чтении файла по пути: " +
                    PATH_TO_INFLUENCE, e);
        }
    }

    /**
     * Подсчитывает сумму значений строки матрицы
     *
     * @param array массив со строками, равными числу или "_" - разделителю матрицы
     * @return сумма массива строк
     */
    private double calculateArraySum(String[] array) {
        double sum = 0;
        for (String value : array) {
            if (!MATRIX_DIAGONAL_DELIMITER.equals(value)) {
                sum += Double.parseDouble(value);
            }
        }
        return sum;
    }

    /**
     * Подготавливает данные о стейкхолдерах
     *
     * @param reader             объект BufferedReader с открытым файлом
     * @param stakeholderDataMap мапа с представлением (имя стейкхолдера - его важность)
     * @return массив с именами стейкхолдеров
     * @throws DataValidationException ошибка при несоответствии наименований стейкхолдеров
     */
    @SneakyThrows
    private String[] prepareStakeholders(BufferedReader reader, Map<String, Importance> stakeholderDataMap) {
        String[] stakeholders;
        stakeholders = reader.readLine().split(" \\| ");
        if (stakeholderDataMap.isEmpty()) {
            for (String stakeholder : stakeholders) {
                stakeholderDataMap.put(stakeholder, new Importance());
            }
        } else if (!Set.of(stakeholders).equals(stakeholderDataMap.keySet())) {
            throw new DataValidationException("Несоответствие наименований заинтересованных сторон");
        }
        return stakeholders;
    }
}