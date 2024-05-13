package ru.croc.stakeholder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// Класс, отвечающий за обработку информации о стейкхолдерах
public class StakeHolderAnalyzer {

    // Матрица отношений между стейкохолдерами и имена самих стейкохолдеров в
    // соответствующем порядке + основные операции (для текущей задачи
    // достаточно суммирования строки)
    public record StakeHoldersRelations(List<String> stakeholders,
                                        ArrayList<ArrayList<Double>> matrix) {
        public Map<String, Double> sum() {
            Map<String, Double> stakeholdersImportance = new HashMap<>();
            for (int i = 0; i < stakeholders.size(); ++i) {
                stakeholdersImportance.put(
                        stakeholders.get(i),
                        matrix()
                                .get(i)
                                .stream()
                                .mapToDouble(Double::doubleValue)
                                .sum());
            }
            return stakeholdersImportance;
        }
    }

    // Метод, который читает файл с матрицей какого-либо параметра
    // Приватный, так как формат файла специфичен именно для анализа
    // информации об отношениях стейкхолдеров
    private static StakeHoldersRelations parseMatrixFile(Path pathToFile) {
        try {
            List<String> lines = Files.readAllLines(pathToFile);
            if (lines.size() <= 1) {
                throw new RuntimeException("Некорректный формат файла матрицы:"
                                           + " " + pathToFile
                                           + " содержит <= 1 строк");
            }
            // В задании сказано, что ЗС разделены через "|", но в примере
            // они разделены через " | ", поэтому предполагаю, что пробелы
            // нужно обрезать
            List<String> stakeholders = Arrays.stream(lines.get(0).split(
                    "\\|")).map(String::strip).toList();
            ArrayList<ArrayList<Double>> matrix =
                    new ArrayList<>(stakeholders.size());
            int offset = 1;
            for (int i = 0; i < lines.size() - offset; i++) {
                matrix.add(new ArrayList<>(stakeholders.size()));
                String[] scores = lines.get(i + offset).split(" ");
                if (scores.length != lines.size() - offset) {
                    throw new RuntimeException(
                            "Некорректный формат файла матрицы:"
                            + " " + pathToFile + " матрица"
                            + " не квадратная");
                }
                for (int j = 0; j < scores.length; j++) {
                    if (j != i) {
                        matrix.get(i).add(Double.parseDouble(scores[j]));
                    } else {
                        matrix.get(i).add(0.0);
                    }
                }
            }
            return new StakeHoldersRelations(stakeholders, matrix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод, преобразующий 2 Map с информацией по параметру стейкхолдера и
    // в точки на графике важности, и вызывающий поиск важнейших по графику
    public static List<String> getMostImportantStakeHolders(
            Map<String, Double> interestImportance,
            Map<String, Double> influenceImportance) {
        List<QuadrantSeparator.Point> points =
                new ArrayList<>(influenceImportance.size());
        for (Map.Entry<String, Double> nameInterest :
                interestImportance.entrySet()) {
            Double influence = influenceImportance.get(nameInterest.getKey());
            if (influence == null) {
                throw new RuntimeException("Некорректные матрицы: "
                                           + "стейкхолдеры влияния и "
                                           + "заинтересованности не совпадают"
                                           + ". Влияние: "
                                           + influenceImportance.keySet()
                                           + " Заинтересованность: "
                                           + interestImportance.keySet());
            }
            points.add(new QuadrantSeparator.Point(nameInterest.getKey(),
                                                   nameInterest.getValue(),
                                                   influence));
        }
        return QuadrantSeparator.getMostImportant(points);

    }

    public static List<String> getMostImportantStakeHolders(Path interestPath,
                                                            Path influencePath) {
        Map<String, Double> interestImportance =
                parseMatrixFile(interestPath).sum();
        Map<String, Double> influenceImportance =
                parseMatrixFile(influencePath).sum();
        return getMostImportantStakeHolders(interestImportance,
                                            influenceImportance);
    }

}
