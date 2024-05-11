using System.Globalization;

namespace KrokTestCase2024
{
     class Program
    {
        static void Main(string[] args)
        {
            //Получаем словарь с ключем в виде стейхолдера и значением в виде его строки со значениями.
            Dictionary<string, List<double>> interest = ReadMatrix("interest.txt");
            Dictionary<string, List<double>> influence = ReadMatrix("influence.txt");
            //Получаем количество стейкхолдеров.
            List<string> stakeholders = interest.Keys.ToList();
            //Объевляем новый список, в котором будут перечислены самые важные стейкхолдеры.
            List<string> result = new List<string>();
            //Для определения самых важных стейкхолдеров, нужно поделить количество стейкхолдеров на 2, с этим значением и будут сравниваться значения из списка.
            double stakeholdersCount = interest.Keys.Count / 2;

            for (int i = 0; i < stakeholders.Count; i++)
            {
                //Если суммарное значение интереса И влияния для каждой строки (для каждого стрейкхолдера) больше чем переменная stakeholdersCount, то добавляем стейкхолдера в список result.
                if (interest[stakeholders[i]].Sum() > stakeholdersCount && influence[stakeholders[i]].Sum() > stakeholdersCount)
                {
                    result.Add(stakeholders[i]);
                }
            }
            //Выводим полученные данные в файл result.txt.
            File.WriteAllLines("result.txt", result);
        }
        //Данный метод принимает исходный файл и возвращает словарь с ключем в виде стейхолдера (Stakeholder[i]) и значением в виде списка со значениями для каждого нашего стейкхолдера.
        static Dictionary<string, List<double>> ReadMatrix(string fileName)
        {
            //Получаем данные из файла в массив
            string[] ourFile = File.ReadAllLines(fileName);
            //Обрабатываем количество стейкхолдеров, разделяя их по символу '|' и удаляя пробелы.
            List<string> stakeholders = ourFile[0].Split('|').Select(x => x.Trim()).ToList();
            //Создаем пустой словарь
            Dictionary<string, List<double>> matrix = new Dictionary<string, List<double>>();

            for (int i = 1; i < ourFile.Length; i++)
            {
                //Перебираем наш файл и записываем значения в список, вместо символа "_" будет использоваться 0. 
                List<double> values = ourFile[i].Split(' ').Select(x => x == "_" ? 0 : double.Parse(x, CultureInfo.InvariantCulture)).ToList();
                //Каждому стейкхолдеру(ключу) присваиваем его значение, которые представлено в виде списка значений.
                matrix.Add(stakeholders[i - 1], values);
            }
            return matrix;
        }
    }
}
