package ru.croc;

import ru.croc.data.Importance;
import ru.croc.exception.DataValidationException;
import ru.croc.exception.FileOperationException;
import ru.croc.exception.InitializationException;
import ru.croc.service.StakeholderService;
import ru.croc.utils.file.ResourcesReader;
import ru.croc.utils.file.ResourcesWriter;

import java.util.Map;

public class Main {
    private static final String DEFAULT_PATH_TO_INFLUENCE = "./resources/influence.txt";
    private static final String DEFAULT_PATH_TO_INTEREST = "./resources/interest.txt";
    private static final String DEFAULT_PATH_TO_RESULT = "./resources/result.txt";

    public static void main(String[] args) {
        try {
            ResourcesReader resourcesReader;
            ResourcesWriter resourcesWriter;
            if (args.length == 3) {
                resourcesReader = new ResourcesReader(args[0], args[1]);
                resourcesWriter = new ResourcesWriter(args[2]);
            } else {
                resourcesReader = new ResourcesReader(DEFAULT_PATH_TO_INFLUENCE, DEFAULT_PATH_TO_INTEREST);
                resourcesWriter = new ResourcesWriter(DEFAULT_PATH_TO_RESULT);
            }

            Map<String, Importance> stakeholderDataMap = resourcesReader.getStakeholderDataMap();

            StakeholderService stakeholderService = new StakeholderService(stakeholderDataMap);
            String mostImportantStakeholders = stakeholderService.findMostImportantStakeholders();

            resourcesWriter.writeMostImportantStakeholders(mostImportantStakeholders);
        } catch (DataValidationException | FileOperationException | InitializationException e) {
            System.out.println(e.getMessage());
        }
    }
}
