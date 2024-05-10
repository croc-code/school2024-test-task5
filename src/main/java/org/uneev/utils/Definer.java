package org.uneev.utils;

import org.apache.commons.lang3.StringUtils;
import org.uneev.exceptions.InvalidFilePatternException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// We don't need to create multiple definer objects, so we'll use an enum based singleton
public enum Definer {

    // Definer for finding the most important stakeholders
    // We can complete an enum with others definers if necessary
    TOPMOST_STAKEHOLDER_DEFINER(
            "src/main/resources/interest.txt",
            "src/main/resources/influence.txt",
            "src/main/resources/result.txt"
    );

    private final String interestFilePath;  // File path for the interest matrix file
    private final String influenceFilePath;  // File path for the influence matrix file
    private final String resultFilePath;  // File where the result of defining will be written

    Definer(String interestFilePath, String influenceFilePath, String resultFilePath) {
        this.interestFilePath = interestFilePath;
        this.influenceFilePath = influenceFilePath;
        this.resultFilePath = resultFilePath;
    }

    // Writes most important stakeholders to the resultFilePath
    public void writeTopmostStakeholdersToFile() {
        String[] topmostStakeholders = defineTopmostStakeholders();

        try (PrintWriter printWriter = new PrintWriter(resultFilePath)) {
            Arrays.stream(topmostStakeholders)
                    .forEach(printWriter::println);
        } catch (IOException e) {
            System.out.println("Can't write to the file");
            throw new RuntimeException(e);
        }
    }

    // Defines most important stakeholders (for internal use)
    private String[] defineTopmostStakeholders() {
        Map<String, Double> interestRankMap = calculateRank(interestFilePath);
        Map<String, Double> influenceRankMap = calculateRank(influenceFilePath);

        if (!influenceRankMap.keySet().equals(interestRankMap.keySet())) {
            throw new InvalidFilePatternException(
                    "Stakeholders in 'interest' and 'influence' files does not match"
            );
        }

        double threshold = (double) influenceRankMap.size() / 2;

        return interestRankMap.keySet().stream()
                .filter(stakeholder -> interestRankMap.get(stakeholder) > threshold
                        && influenceRankMap.get(stakeholder) > threshold)
                .toArray(String[]::new);
    }

    // Calculates rank of stakeholders based on the received file path (for internal use)
    // Returns map (key - stakeholder string representation, value - received rank)
    private Map<String, Double> calculateRank(String filePath) {
        Map<String, Double> ranks = new HashMap<>();

        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Can't read from the file");
            throw new RuntimeException(e);
        }

        String[] stakeholders = lines.get(0).split(" \\| ");

        if (stakeholders.length != lines.size() - 1) {
            throw new InvalidFilePatternException(
                    "The number of ranks must match the number of stakeholders"
            );
        }

        for (int i = 0; i < stakeholders.length; i++) {
            String[] symbols = lines.get(i+1).split("\s+");

            if (symbols.length != stakeholders.length)
                throw new InvalidFilePatternException("The number of ranks does not match the number of stakeholders");

            double sum = Arrays.stream(symbols)
                    .filter(StringUtils::isNumeric)
                    .mapToDouble(Double::parseDouble)
                    .sum();

            ranks.put(stakeholders[i], sum);
        }
        return ranks;
    }
}
