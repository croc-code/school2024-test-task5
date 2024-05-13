package ru.croc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.exception.IncorrectDataException;
import ru.croc.stakeholder.Stakeholder;

import java.util.ArrayList;
import java.util.List;

import static ru.croc.stakeholder.StakeholderFinder.getMostImportantStakeholders;

public class StakeholderFinderTest {
    List<Stakeholder> emptyList = new ArrayList<>();

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС")
    void findMostImportantStakeholdersTest() {
        List<Stakeholder> stakeholderList = List.of(
                new Stakeholder("stakeholder1", 1.0, 1.5),
                new Stakeholder("stakeholder2", 3.5, 4.0),
                new Stakeholder("stakeholder3", 2.0, 1.0),
                new Stakeholder("stakeholder4", 3.0, 3.5),
                new Stakeholder("stakeholder5", 1.5, 1.0)
        );
        List<Stakeholder> expectedAnswer = List.of(
                new Stakeholder("stakeholder2", 3.5, 4.0),
                new Stakeholder("stakeholder4", 3.0, 3.5)
        );
        Assertions.assertEquals(expectedAnswer, getMostImportantStakeholders(stakeholderList));
    }

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС в случае попадания ЗС на границы квадратов")
    void findMostImportantStakeholdersOnBorderTest() {
        List<Stakeholder> stakeholderList = List.of(
                new Stakeholder("stakeholder1", 2.0, 3.0),
                new Stakeholder("stakeholder2", 3.0, 2.0),
                new Stakeholder("stakeholder3", 2.0, 2.0),
                new Stakeholder("stakeholder4", 1.0, 2.0)
        );
        List<Stakeholder> expectedAnswer = List.of(
                new Stakeholder("stakeholder1", 2.0, 3.0),
                new Stakeholder("stakeholder2", 3.0, 2.0),
                new Stakeholder("stakeholder3", 2.0, 2.0)
        );
        Assertions.assertEquals(expectedAnswer, getMostImportantStakeholders(stakeholderList));
    }

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС в случае отсуствия самых важных ЗС")
    void findMostImportantStakeholdersWithoutRightAnswerTest() {
        List<Stakeholder> stakeholderList = List.of(
                new Stakeholder("stakeholder1", 0.5, 2.0),
                new Stakeholder("stakeholder2", 2.0, 0.5)
        );
        Assertions.assertEquals(emptyList, getMostImportantStakeholders(stakeholderList));
    }

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС в случае пустого списка ЗС")
    void findMostImportantStakeholdersOnEmptyListTest() {
        Assertions.assertEquals(emptyList, getMostImportantStakeholders(emptyList));
    }

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС в случае незаданного списка ЗС")
    void findMostImportantStakeholdersOnNullListTest() {
        Assertions.assertThrows(IncorrectDataException.class, () -> getMostImportantStakeholders(null));
    }

    @Test
    @DisplayName("Тестирование метода поиска самых важных ЗС в случае списка с незаданными ЗС")
    void findMostImportantStakeholdersOnNullElementsListTest() {
        List<Stakeholder> stakeholderList = new ArrayList<>();
        stakeholderList.add(null);
        Assertions.assertThrows(IncorrectDataException.class, () -> getMostImportantStakeholders(stakeholderList));
    }
}
