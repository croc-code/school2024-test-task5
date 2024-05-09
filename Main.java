import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

    private static final String PATH_TO_INTEREST_TXT = "interest.txt";      // путь до файла interest.txt
    private static final String PATH_TO_INFLUENCE_TXT = "influence.txt";    // путь до файла influence.txt
    private static final String PATH_TO_RESULT_TXT = "result.txt";          // путь до файла result.txt
    private static final String REGEXP_RANK = " ";                          // регулярное выражение для считывания в матрице попарного сравнения
    private static final String REGEXP_STAKEHOLDER = " \\| ";               // регулярное выражение для считывания имен в матрице попарного сравнения
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        String[] stakeholders = readStakeholders(); // Предполагаем, что имена стейкхолдеров одинаковы в обоих файлах
        double[][] interestMatrix = readMatrix(PATH_TO_INTEREST_TXT, stakeholders.length);
        double[][] influenceMatrix = readMatrix(PATH_TO_INFLUENCE_TXT, stakeholders.length);

        double[] interestRanks = calculateRanks(interestMatrix);
        double[] influenceRanks = calculateRanks(influenceMatrix);

        writeToResultFile(stakeholders, interestRanks, influenceRanks);
    }

    /**
     * Записывает в результирующий файл result.txt имена стейкхолдеров, у которых оба ранга (интерес и влияние)
     * превышают половину общего числа стейкхолдеров.
     *
     * @param stakeholders   массив строк, содержащий имена стейкхолдеров
     * @param interestRanks  массив типа double, содержащий итоговые ранги интереса каждого стейкхолдера
     * @param influenceRanks массив типа double, содержащий итоговые ранги влияния каждого стейкхолдера
     * @throws IOException в случае возникновения ошибки при работе с файловой системой
     */
    private static void writeToResultFile(String[] stakeholders, double[] interestRanks, double[] influenceRanks) throws IOException {
        for (int i = 0; i < stakeholders.length; i++) {
            if (interestRanks[i] > (double) stakeholders.length / 2 && influenceRanks[i] > (double) stakeholders.length / 2) {
                Files.writeString(Path.of(PATH_TO_RESULT_TXT), stakeholders[i] + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
    }

    /**
     * Считывает строку с именами стейкхолдеров из файла, указанного в константе PATH_TO_INTEREST_TXT.
     * Использует регулярное выражение REGEXP для разделения строки на отдельные имена
     *
     * @return массив строк, содержащий имена стейкхолдеров
     * @throws FileNotFoundException если файл, указанный в PATH_TO_INTEREST_TXT, не найден
     */
    private static String[] readStakeholders() throws FileNotFoundException {
        scanner = new Scanner(new File(PATH_TO_INTEREST_TXT));
        String[] stakeholders = scanner.nextLine().split(REGEXP_STAKEHOLDER);
        scanner.close();

        return stakeholders;
    }

    /**
     * Считывает матрицу попарного сравнения из файла
     *
     * @param filename имя файла, содержащего матрицу
     * @param size     размер матрицы (количество стейкхолдеров)
     * @return двумерный массив типа double, представляющий матрицу попарного сравнения
     * @throws FileNotFoundException если файл с указанным именем не найден
     */
    private static double[][] readMatrix(String filename, int size) throws FileNotFoundException {
        scanner = new Scanner(new File(filename));
        scanner.nextLine(); // Пропускаем строку с именами стейкхолдеров

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            String[] row = scanner.nextLine().split(REGEXP_RANK);
            for (int j = 0; j < size; j++) {
                matrix[i][j] = row[j].equals("_") ? 0 : Double.parseDouble(row[j]);
            }
        }

        scanner.close();
        return matrix;
    }

    /**
     * Вычисляет итоговый ранг заинтересованной стороны путем складывания величин в строке матрицы сравнения
     *
     * @param matrix двумерный массив типа double, представляющий матрицу попарного сравнения.
     *               Размер матрицы соответствует количеству ЗС, и каждый элемент матрицы
     *               представляет собой величину влияния или интереса одной ЗС к другой.
     *               На главной диагонали матрицы значения равны "_", так как сравнение ЗС самой с собой некорректно.
     * @return массив типа double, содержащий итоговые ранги каждой ЗС
     */
    private static double[] calculateRanks(double[][] matrix) {
        int size = matrix.length;
        double[] ranks = new double[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ranks[i] += matrix[i][j];
            }
        }

        return ranks;
    }
}
