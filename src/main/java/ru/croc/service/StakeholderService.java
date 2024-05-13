package ru.croc.service;

import ru.croc.data.Importance;
import ru.croc.exception.InitializationException;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для работы со стейкхолдерами
 */
public class StakeholderService {
    private final Map<String, Importance> stakeholderDataMap;

    /**
     * Конструктор
     *
     * @param stakeholderDataMap мапа с представлением (имя стейкхолдера - его важность)
     * @throws InitializationException ошибка при переданном null параметре
     */
    public StakeholderService(Map<String, Importance> stakeholderDataMap) {
        if (stakeholderDataMap == null) {
            throw new InitializationException("Передана null мапа");
        }
        this.stakeholderDataMap = stakeholderDataMap;
    }

    /**
     * Находит самых важных стейкхолдеров
     *
     * @return строка с именами самых важных стейкхолдеров, разделенными \n
     */
    public String findMostImportantStakeholders() {
        double size = stakeholderDataMap.size();

        return stakeholderDataMap.entrySet().stream()
                .filter(stringImportance -> stringImportance.getValue().getInfluence() >= size / 2 &&
                        stringImportance.getValue().getInterest() >= size / 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining("\n"));
    }
}