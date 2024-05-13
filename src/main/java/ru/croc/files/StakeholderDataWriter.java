package ru.croc.files;

import ru.croc.exception.FileInteractionException;
import ru.croc.stakeholder.Stakeholder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Класс записи в файл.
 */
public class StakeholderDataWriter {
    /**
     * Метод записи имён заинтересованных сторон в файл.
     *
     * @param stakeholderList список объектов ЗС
     * @param filePath путь к файлу с ответом
     */
    public static void writeStakeholderNames(List<Stakeholder> stakeholderList, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
            stakeholderList.forEach(stakeholder ->
                    {
                        try {
                            Files.writeString(path, stakeholder.name() + "\n",
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.WRITE,
                                    StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (IOException e) {
            throw new FileInteractionException("Ошибка при записи в файл: " + filePath, e);
        }
    }
}
