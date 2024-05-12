package ru.croc.stakeholder;

import ru.croc.exception.IncorrectDataException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс поиска нужных стейкхолдеров в общем списке.
 */
public class StakeholderFinder {
    /**
     * Метод поиска самых важных ЗС из всех.
     *
     * @param stakeholderList общий список ЗС
     * @return список самых важных ЗС
     */
    public static List<Stakeholder> getMostImportantStakeholders(List<Stakeholder> stakeholderList) {
        validateStakeholderList(stakeholderList);
        int stakeholdersNumber = stakeholderList.size();
        return stakeholderList.stream()
                .filter(stakeholder -> checkIfStakeholderIsMostImportant(stakeholder, stakeholdersNumber))
                .collect(Collectors.toList());
    }

    /**
     * Метод проверки, является ли ЗС одним из самых важных.
     *
     * @param stakeholder объект ЗС
     * @param stakeholdersNumber общее количество ЗС
     * @return булевское значение, показывающее, является ли ЗС одной из самых важных
     */
    private static boolean checkIfStakeholderIsMostImportant(Stakeholder stakeholder, int stakeholdersNumber) {
        return stakeholder.interest() >= (double) stakeholdersNumber / 2 &&
                stakeholder.influence() >= (double) stakeholdersNumber / 2;
    }

    /**
     * Метод валидации списка ЗС.
     *
     * @throws IncorrectDataException если список ЗС некорректен
     */
    private static void validateStakeholderList(List<Stakeholder> stakeholderList) {
        if (stakeholderList == null) {
            throw new IncorrectDataException("Лист ЗС равен null");
        }
        if (stakeholderList.stream().anyMatch(Objects::isNull)) {
            throw new IncorrectDataException("Лист ЗС содержит null");
        }
    }
}
