import java.io.*;

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
     * Считывает стейкхолдеров из первой строки файла pathToInterestMatrix.
     *
     * @return массив строк, содержащий стейкхолдеров, прочитанных из файла
     * @throws RuntimeException
     */
    private static String[] readStakeholdersFromFile() {
        String[] stakeholders;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToInterestMatrix))) {
            String line = reader.readLine();
            String[] rawStakeholders = line.split("\\|"); //Разделяет строку на отдельные элементы согласно регулярному выражению "\\|"
            stakeholders = new String[rawStakeholders.length];
            for (int i = 0; i < rawStakeholders.length; i++) {
                stakeholders[i] = rawStakeholders[i].trim(); //Убирает лишние пробелы при добавлении в результирующий массив
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stakeholders;
    }

    /**
     * Считывает матрицу из файла.
     *
     * @param size размер матрицы
     * @param path путь к файлу с матрицей
     * @return двумерный массив с матрицей
     */
    private static double[][] readMatrixFromFile(int size, String path) {
        double[][] matrix = new double[size][size];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine(); //Пропускает строку со стейкхолдерами
            for (int i = 0; i < size; i++) {
                String[] line = reader.readLine().split(" ");
                for (int j = 0; j < size; j++) {
                    if (!line[j].equals("_")) {
                        matrix[i][j] = Double.parseDouble(line[j]);
                    } else {
                        matrix[i][j] = 0;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }

    /**
     * Возвращает индексы стейкхолдеров: если стейкхолдер является важным - его индекс, иначе -1.
     *
     * @param interestMatrix  матрица интересов
     * @param influenceMatrix матрица влияния
     * @param size            размер матрицы
     * @return массив индексов стейкхолдеров
     */
    private static int[] getStakeholdersIndex(double[][] interestMatrix, double[][] influenceMatrix, int size) {
        int[] result = new int[size];

        double[] interestRank = getRanks(interestMatrix);
        double[] influenceRank = getRanks(influenceMatrix);


        for (int i = 0; i < result.length; i++) {
            result[i] = (interestRank[i] != 0 && influenceRank[i] != 0) ? i : -1;
        }
        return result;
    }

    /**
     * Возвращает массив рангов стейкхолдеров согласно условию.
     * Записывается ранг тех, кто располагаются в правом верхнем квадранте матрицы.
     *
     * @param matrix входная матрица
     * @return массив рангов стейкхолдеров с учетом условий
     */
    private static double[] getRanks(double[][] matrix) {
        int size = matrix.length;
        double rankConstraints = size / 2.0;
        double[] ranks = new double[size];

        for (int i = 0; i < size; i++) {
            double rank = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rank += matrix[i][j];
            }
            if (rank >= rankConstraints) {
                ranks[i] = rank;
            }
        }
        return ranks;
    }

    /**
     * Записывает стейкхолдеров, указанных по индексам в массиве (индекс которых не равен -1), в файл "result.txt".
     *
     * @param idxOfStakeholders массив индексов стейкхолдеров, которые будут записаны
     * @param stakeholders      массив стейкхолдеров
     */
    private static void writeResToFile(int[] idxOfStakeholders, String[] stakeholders) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))) {

            for (int i = 0; i < idxOfStakeholders.length; i++) {
                if (idxOfStakeholders[i] != -1) {
                    writer.write(stakeholders[idxOfStakeholders[i]]);
                    writer.newLine();
                    System.out.print(stakeholders[idxOfStakeholders[i]]);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        setFilePath(args);
        String[] stakeholders = readStakeholdersFromFile(); //Допущение: порядок и количество стейкхолдеров в обеих матрицах одинаков
        double[][] interestMatrix = readMatrixFromFile(stakeholders.length, pathToInterestMatrix);
        double[][] influenceMatrix = readMatrixFromFile(stakeholders.length, pathToInfluenceMatrix);
        int[] resIndex = getStakeholdersIndex(interestMatrix, influenceMatrix, stakeholders.length);
        writeResToFile(resIndex, stakeholders);
    }
}
