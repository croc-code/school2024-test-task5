import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.croc.stakeholder.StakeHolderAnalyzer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StakeHolderTest {
    @Test
    public void parseFileTest() throws URISyntaxException {
        Path influencePath = Paths.get(
                StakeHolderTest.class.getResource("influence.txt").toURI());
        Path interestPath = influencePath;
        Assertions.assertEquals(
                List.of("Stakeholder 2", "Stakeholder 4"),
                StakeHolderAnalyzer.getMostImportantStakeHolders(
                        interestPath, influencePath));
    }

    @Test
    public void testIncorrectMatrixExceptions() throws URISyntaxException {
        Path influencePath = Paths.get(
                StakeHolderTest.class.getResource(
                        "influence_incorrect_matrix.txt").toURI());
        Path interestPath = Paths.get(
                StakeHolderTest.class.getResource(
                        "influence.txt").toURI());
        Assertions.assertThrows(
                RuntimeException.class,
                ()->StakeHolderAnalyzer
                        .getMostImportantStakeHolders(
                                interestPath,
                                influencePath));
    }

    @Test
    public void testTooShortMatrixExceptions() throws URISyntaxException {
        Path influencePath = Paths.get(
                StakeHolderTest.class.getResource(
                        "influence_incorrect_short.txt").toURI());
        Path interestPath = Paths.get(
                StakeHolderTest.class.getResource(
                        "influence.txt").toURI());
        Assertions.assertThrows(
                RuntimeException.class,
                ()->StakeHolderAnalyzer
                        .getMostImportantStakeHolders(
                                interestPath,
                                influencePath));
    }

    @Test
    public void testDifferentStakeHoldersExceptions() throws URISyntaxException {
        Path influencePath = Paths.get(
                StakeHolderTest.class.getResource(
                        "influence.txt").toURI());
        Path interestPath = Paths.get(
                StakeHolderTest.class.getResource(
                        "interest_different.txt").toURI());
        Assertions.assertThrows(
                RuntimeException.class,
                ()->StakeHolderAnalyzer
                        .getMostImportantStakeHolders(
                                interestPath,
                                influencePath));
    }
}
