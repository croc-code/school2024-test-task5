package ru.croc.stakeholder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args)
            throws IOException, URISyntaxException {
        Path interestPath;
        Path influencePath;
        if (args.length == 0) {
            interestPath = Path.of("interest.txt");
            influencePath = Path.of("influence.txt");
        } else {
            interestPath = Path.of(args[0]);
            influencePath = Path.of(args[1]);
        }
        List<String> important =
                StakeHolderAnalyzer.getMostImportantStakeHolders(interestPath,
                                                           influencePath);
        String answerPath = "result.txt";
        if (args.length > 2) {
            answerPath = args[2];
        }
        Files.write(Path.of(answerPath), important);

    }
}
