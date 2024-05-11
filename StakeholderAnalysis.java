import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StakeholderAnalysis {
    private static String pathToInterestMatrix;
    private static String pathToInfluenceMatrix;

    /**
     * Устанавливает пути к файлам матрицы интересов и влияния на основе переданных аргументов.
     * Если аргументы не были переданы, то устанавливаются пути по умолчанию.
     *
     * @param arg массив аргументов, содержащий пути к файлам
     * @throws IOException
     */
    private static void setFilePath(String[] arg) throws IOException {
        pathToInterestMatrix = (arg.length == 2) ? arg[0] : "interest.txt";
        pathToInfluenceMatrix = (arg.length == 2) ? arg[1] : "influence.txt";
    }

    /**
     * Считывает из файла имена стейкхолдеров и их ранги и возвращает их в виде HashMap.
     *
     * @param path путь к файлу с матрицей
     * @return HashMap, где ключами являются имена стейкхолдеров, а значениями - их ранги
     */
    private static HashMap<String, Double> getStakeholdersWithRanksFromFile(String path) {
        HashMap<String, Double> stakeholdersWithRanks = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            //Считывание стейкхолдеров
            String lineStakeholders = reader.readLine();
            String[] rawStakeholders = lineStakeholders.split("\\|"); //Разделяет строку на отдельные элементы согласно регулярному выражению "\\|"
            String[] stakeholders = new String[rawStakeholders.length];
            for (int i = 0; i < rawStakeholders.length; i++) {
                stakeholders[i] = rawStakeholders[i].trim(); //Убирает лишние пробелы при добавлении в результирующий массив
            }
            //Считывание матрицы
            for (int i = 0; i < stakeholders.length; i++) {
                String[] line = reader.readLine().split(" ");
                double rowRank = 0;
                for (int j = 0; j < stakeholders.length; j++) {
                    if (!line[j].equals("_")) {
                        rowRank += Double.parseDouble(line[j]);
                    }
                }
                stakeholdersWithRanks.put(stakeholders[i], rowRank);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stakeholdersWithRanks;
    }

    /**
     * Находит стейкхолдеров с рангами выше среднего по матрице.
     *
     * @param map HashMap, содержащая пары ключ-значение, где ключом является имя стейкхолдера, а значением - его ранг
     * @return HashMap, содержащая только стейкхолдеров с рангами выше среднего
     */
    private static HashMap<String, Double> findMainStakeholders(HashMap<String, Double> map) {
        double rank = map.values().stream().mapToDouble(Double::doubleValue).sum(); //Ранг стейкхолдера
        double averageRank = rank / map.size(); //Средний ранг по матрице

        Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            if (entry.getValue() < averageRank) { //Оставляет только важных по матрице (которые больше или равны среднему рангу)
                iterator.remove();
            }
        }
        return map;
    }

    /**
     * Определяет важных стейкхолдеров и записывает в файл 'result.txt'.
     *
     * @param interest  HashMap, стейкхолдеры из матрицы интересов с рангами, которые подошли по условию
     * @param influence HashMap, стейкхолдеры из матрицы влияния с рангами, которые подошли по условию
     */
    private static void writeMainStakeholders(HashMap<String, Double> interest, HashMap<String, Double> influence) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))) {
            for (String key : interest.keySet()) {
                if (influence.containsKey(key)) { //Если у стейкхолдера оба ранга удовлетворяют условию, то он попадает в файл важных стейкхолдеров
                    writer.write(key);
                    writer.newLine();
                    System.out.println(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        setFilePath(args);

        HashMap<String, Double> interestStakeholders = getStakeholdersWithRanksFromFile(pathToInterestMatrix);
        HashMap<String, Double> influenceStakeholders = getStakeholdersWithRanksFromFile(pathToInfluenceMatrix);

        HashMap<String, Double> mainInterestStakeholders = findMainStakeholders(interestStakeholders);
        HashMap<String, Double> mainInfluenceStakeholders = findMainStakeholders(influenceStakeholders);

        writeMainStakeholders(mainInterestStakeholders, mainInfluenceStakeholders);
    }
}
