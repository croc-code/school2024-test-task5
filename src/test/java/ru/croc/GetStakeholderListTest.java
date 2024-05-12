package ru.croc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.exception.FileInteractionException;
import ru.croc.exception.IncorrectDataException;
import ru.croc.stakeholder.Stakeholder;

import java.util.List;

import static ru.croc.files.StakeholderDataReader.getStakeholderList;


public class GetStakeholderListTest {
    @Test
    @DisplayName("Тестирование метода составления списка ЗС")
    void getStakeholderListTest() {
        String interestFilePath = "src/test/resources/test1/interest.txt";
        String influenceFilePath = "src/test/resources/test1/influence.txt";

        List<Stakeholder> expectedAnswer = List.of(
                new Stakeholder("Stakeholder 1", 1.0, 1.0),
                new Stakeholder("Stakeholder 2", 3.5, 3.5),
                new Stakeholder("Stakeholder 3", 1.0, 1.0),
                new Stakeholder("Stakeholder 4", 3.0, 3.0),
                new Stakeholder("Stakeholder 5", 1.5, 1.5)
        );
        List<Stakeholder> actualAnswer = getStakeholderList(interestFilePath, influenceFilePath);
        Assertions.assertTrue(
                expectedAnswer.containsAll(actualAnswer) && actualAnswer.containsAll(expectedAnswer));
    }

    @Test
    @DisplayName("Тестирование метода составления списка ЗС при не соответсвующих друг другу матрицах")
    void getStakeholderListOnIncorrectMatricesTest() {
        String interestFilePath = "src/test/resources/test2/interest.txt";
        String influenceFilePath = "src/test/resources/test2/influence.txt";

        Assertions.assertThrows(IncorrectDataException.class,
                () -> getStakeholderList(interestFilePath, influenceFilePath));
    }

    @Test
    @DisplayName("Тестирование метода составления списка ЗС при некорректном пути файла")
    void getStakeholderListOnIncorrectPathTest() {
        String interestFilePath = "wrongPath1";
        String influenceFilePath = "wrongPath2";

        Assertions.assertThrows(FileInteractionException.class,
                () -> getStakeholderList(interestFilePath, influenceFilePath));
    }
}
