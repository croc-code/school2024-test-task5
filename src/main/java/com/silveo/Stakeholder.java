package com.silveo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Stakeholder {
    public static void main(String[] args) {
        try {
            Map<String, Double> interestMap = readMatrixFromFile("src/main/resources/interest.txt");
            Map<String, Double> influenceMap = readMatrixFromFile("src/main/resources/influence.txt");

            List<String> importantStakeholders = determineImportantStakeholders(interestMap, influenceMap);

            writeToFile(importantStakeholders, "src/main/resources/result.txt");
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this method gets matrix from the file and transforms it to the map
    private static Map<String, Double> readMatrixFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        //getting stakeholders list from 1st line of the file
        String[] stakeholders = lines.get(0).split("\\|");
        double[][] matrix = new double[stakeholders.length][stakeholders.length];

        //I used LinkedHashMap to save the insertion order
        Map<String, Double> resultMap = new LinkedHashMap<>();

        //parsing file making doubles from strings and checks for "_"
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(" ");
            for (int j = 0; j < values.length; j++) {
                if (!"_".equals(values[j])) {
                    matrix[i - 1][j] = Double.parseDouble(values[j]);
                }
            }
        }

        //counting sum for each line (stakeholder rank)
        for (int i = 0; i < stakeholders.length; i++) {
            double sum = 0;
            for (int j = 0; j < stakeholders.length; j++) {
                sum += matrix[i][j];
            }
            resultMap.put(stakeholders[i].trim(), sum);
        }
        System.out.println(resultMap.values());
        return resultMap;
    }

    //this method determines if the stakeholder is important enough to be in result.txt
    private static List<String> determineImportantStakeholders(Map<String, Double> interestMap, Map<String, Double> influenceMap) {
        //calculating average values
        double interestAverage = interestMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double influenceAverage = influenceMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);

        List<String> importantStakeholders = new ArrayList<>();

        //if stakeholders rank is above the average sum for both interest and influence, then it is located in the upper right square
        for (String stakeholder : interestMap.keySet()) {
            if (interestMap.get(stakeholder) > interestAverage && influenceMap.get(stakeholder) > influenceAverage) {
                importantStakeholders.add(stakeholder);
            }
        }

        return importantStakeholders;
    }

    private static void writeToFile(List<String> stakeholders, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, stakeholders);
    }
}