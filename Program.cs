namespace Stakeholder
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // названия входных файлов
            const string INFLUENCE = "influence.txt";
            const string INTEREST = "interest.txt";
            const string RESULT = "result.txt";

            // для записи в файл конечных стейкхолдеров
            List<string> resultStakeholders = new List<string>();

            // рассчитываем путь до нашей директории, где мы запускаем программу
            string path = Directory.GetCurrentDirectory().Split("bin")[0];

            // проверяем, существует ли файл "influence.txt"
            if (File.Exists(path + INFLUENCE))
            {
                string[] file = File.ReadAllLines(path + INFLUENCE);

                if (file != null)
                {
                    string[] nameStakeholders = file[0].Split(" | ");

                    // подсчет всех стейкхолдеров и необходимое значения для преодоления границы
                    int countStakeholder = nameStakeholders.Length;
                    double checkStakeholders = IdentifImportantStakeholder(countStakeholder);

                    // вызываем функцию для получения итоговых рангов стейкхолдеров в матрице влияния
                    double[] ratingStakeholdInfluence = RatingStakeholder(file, countStakeholder);

                    if (File.Exists(path + INTEREST))
                    {
                        file = File.ReadAllLines(path + INTEREST);

                        if (file != null)
                        {
                            double[] ratingStakeholdInterest = RatingStakeholder(file, countStakeholder);

                            for (int value = 0; value < countStakeholder; value++)
                            {
                                // проверяем значения стейкхолдеров в двух сферах (влияние и интерес).
                                // Если оно больше и во влиянии, и в интересе, то сохраняем заинтересованную сторону
                                if (ratingStakeholdInterest[value] >= checkStakeholders
                                    && ratingStakeholdInfluence[value] >= checkStakeholders)
                                {
                                    resultStakeholders.Add(nameStakeholders[value]);
                                }
                            }
                            // записываем в файл стейкхолдеров, которые являются самыми важными
                            // (находятся в правом верхнем квадрате графика)
                            File.WriteAllLines(path + RESULT, resultStakeholders);
                        }
                    }
                }
            }
        }

        // функция, в которой определяем грань значений для важных стейкхолдеров
        static double IdentifImportantStakeholder(int countStakeholder)
        {
            if (countStakeholder % 2 == 0) return countStakeholder / 2;
            
            return countStakeholder / 2 + 0.5;
        }

        // фукнция для получения итоговых рангов в матрице
        static double[] RatingStakeholder(string[] file, int countHolder)
        {
            double[] ratingStakeholders = new double[countHolder];

            for (int line = 1; line < file.Length; line++)
            {
                // заменяем все _ на нули, чтобы при счете их не учитывать
                file[line] = file[line].Replace("_", "0");
                // заменяем точки на запятые, чтобы из строки перевести в double 
                file[line] = file[line].Replace('.', ',');
                // переводим все числа в тип double
                ratingStakeholders[line - 1] = file[line].Split().Select(x => double.Parse(x)).ToArray().Sum();
            }
            return ratingStakeholders;
        }
    }
}