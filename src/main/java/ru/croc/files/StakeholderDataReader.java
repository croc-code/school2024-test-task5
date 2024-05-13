package ru.croc.files;

import ru.croc.exception.FileInteractionException;
import ru.croc.exception.IncorrectDataException;
import ru.croc.stakeholder.Stakeholder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Класс считывания данных о заинтересованных сторонах из файлов.
 */
public class StakeholderDataReader {
    /**
     * Метод формирования полных данных о ЗС.
     *
     * @param interestFilePath Путь к файлу с матрицей интереса
     * @param influenceFilePath Путь к файлу с матрицей влияния
     * @return список объектов ЗС
     */
    public static List<Stakeholder> getStakeholderList(String interestFilePath, String influenceFilePath) {
        Map<String, Double> interestsMap;
        Map<String, Double> influencesMap;
        interestsMap = readMatrix(interestFilePath);
        influencesMap = readMatrix(influenceFilePath);

        if (!checkStakeholdersSetsEquality(interestsMap.keySet(), influencesMap.keySet())) {
            throw new IncorrectDataException(
                    "Матрицы интереса и влияния содержат несовпадающие наборы заинтересованных сторон");
        }

        List<Stakeholder> stakeholderList = new ArrayList<>();
        influencesMap.keySet().forEach(name ->
                stakeholderList.add(new Stakeholder(name, interestsMap.get(name), influencesMap.get(name)))
        );
        return stakeholderList;
    }

    /**
     * Метод сравнения множеств имён ЗС, полученных из файлов.
     *
     * @param a первое множество имён ЗС
     * @param b второе множество имён ЗС
     * @return логическое значение, показывающее, равны ли множества имён ЗС
     */
    private static boolean checkStakeholdersSetsEquality(Set<String> a, Set<String> b) {
        return b.containsAll(a) && a.containsAll(b);
    }

    /**
     * Метод получения данных о значениях интереса/влияния из файла.
     *
     * @param filePath Путь к файлу матрицей интереса/влияния
     * @return словарь <имя ЗС - значение интереса/влияния>
     */
    public static Map<String, Double> readMatrix(String filePath) {
        Map<String, Double> ratingMap = new HashMap<>();
        List<String> stakeholderNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String[] parsedFileString = reader.readLine().split(" \\| ");
            stakeholderNames.addAll(Arrays.asList(parsedFileString));
            for (String stakeholderName : stakeholderNames) {
                double stakeholderRating = Arrays.stream(reader.readLine().split(" "))
                        .filter(string -> !Objects.equals(string, "_"))
                        .mapToDouble(Double::parseDouble)
                        .sum();
                ratingMap.put(stakeholderName, stakeholderRating);
            }
        } catch (IOException e) {
            throw new FileInteractionException("Ошибка при чтении файла: " + filePath, e);
        }
        return ratingMap;
    }
}
