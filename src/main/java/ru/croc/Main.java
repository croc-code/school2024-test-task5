package ru.croc;

import ru.croc.exception.FileInteractionException;
import ru.croc.exception.IncorrectDataException;
import ru.croc.stakeholder.Stakeholder;

import java.util.List;

import static ru.croc.files.StakeholderDataReader.getStakeholderList;
import static ru.croc.files.StakeholderDataWriter.writeStakeholderNames;
import static ru.croc.stakeholder.StakeholderFinder.getMostImportantStakeholders;

/**
 * Основной класс для решения задачи о ЗС.
 */
public class Main {
    //путь к файлу с матрицей интереса
    private final static String INTEREST_FILE_PATH = "src/main/resources/matrix/interest.txt";

    //путь к файлу с матрицей влияния
    private final static String INFLUENCE_FILE_PATH = "src/main/resources/matrix/influence.txt";

    //путь к файлу с ответом
    private final static String MOST_IMPORTANT_STAKEHOLDERS_FILE_PATH = "src/main/resources/result.txt";

    public static void main(String[] args) {
        try {
            List<Stakeholder> stakeholderList = getStakeholderList(INTEREST_FILE_PATH, INFLUENCE_FILE_PATH);
            writeStakeholderNames(getMostImportantStakeholders(stakeholderList), MOST_IMPORTANT_STAKEHOLDERS_FILE_PATH);
        }
        catch (FileInteractionException | IncorrectDataException e) {
            System.out.println(e.getMessage());
        }
    }
}