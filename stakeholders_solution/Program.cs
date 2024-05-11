using System.Collections;
using System.Collections.Generic;

internal class Program
{
  private static async Task Main(string[] args)
  {
    // Словарь для хранения связки 
    // Стейкхолдер - Координаты
    // Stakeholder - [x,y]
    Dictionary<string, double[]> StakeholdersMap = new Dictionary<string, double[]>();

    string pathToInterestFile = Path.Combine(AppContext.BaseDirectory, @"StakeholdersData\interest.txt");
    string pathToInfluenceFile = Path.Combine(AppContext.BaseDirectory, @"StakeholdersData\influence.txt");
    string pathToResultFile = Path.Combine(AppContext.BaseDirectory, @"StakeholdersData\result.txt");
    // 
    if(!Directory.Exists(Path.Combine(AppContext.BaseDirectory, @"StakeholdersData"))){
      Directory.CreateDirectory(Path.Combine(AppContext.BaseDirectory, @"StakeholdersData"));
      Console.WriteLine("Папка StakeholdersData была создана");
    }

    // Проверка на наличие нужных файлов
    if(!File.Exists(pathToInterestFile)){
      throw new Exception("Файл interest.txt не был найден");
    }

    if(!File.Exists(pathToInfluenceFile)){
      throw new Exception("Файл influence.txt не был найден");
    }

    StakeholdersMap = await GetUpdatedStakeholdersMapFromFileAsync(StakeholdersMap, pathToInterestFile, 0);
    StakeholdersMap = await GetUpdatedStakeholdersMapFromFileAsync(StakeholdersMap, pathToInfluenceFile, 1);
    
    for (int i = 0; i < StakeholdersMap.Keys.ToArray().Length; i++)
    {
      // Debug console
      // Оставил для визуализации отношений Стейкхолдер - координаты
      Console.WriteLine(StakeholdersMap.Keys.ToArray()[i] + " => { " + StakeholdersMap[StakeholdersMap.Keys.ToArray()[i]][0] + " : " + StakeholdersMap[StakeholdersMap.Keys.ToArray()[i]][1] +" }");
      // 
    }

    if (await WriteMostImportantStakeholdersToFileAsync(StakeholdersMap, pathToResultFile, StakeholdersMap.Keys.ToArray().Length / 2)){
      Console.WriteLine("\nЗапись в файл results.txt завершена!");
    }
    Console.Write("\nНажмите любую клавишу, чтобы завершить работу");
    Console.ReadKey();
  }

  // Асинхронный метод для считывания данных из 
  // файла и возвращения нового словаря по завершению выполнения
  private static async Task<Dictionary<string, double[]>> GetUpdatedStakeholdersMapFromFileAsync(Dictionary<string, double[]> currentStakeholdersMap, string path, int coordinateIndex){
    // Читаем асинхронно все линии из файла, 
    // чтобы наше приложение не зависало, пока считывает файл
    string[] LinesFromFile = await File.ReadAllLinesAsync(path);

    // Первую линию сплиттим по символу '|', получаем массив стейкхолдеров
    string[] StakeholdersFromHeader = LinesFromFile[0].Split('|');

    // Добавляем имена стейкхолдеров как ключи в наш словарь
    for (int i = 0; i < StakeholdersFromHeader.Length; i++)
    {
      StakeholdersFromHeader[i] = StakeholdersFromHeader[i].TrimStart().TrimEnd();
      if (!currentStakeholdersMap.ContainsKey(StakeholdersFromHeader[i]))
      {
        currentStakeholdersMap.Add(StakeholdersFromHeader[i], new double[2]);
      }
    }

    // Присваеваем значение ( influence или interest ) к ключу ( Стейкхолдеру )
    for (int i = 1; i < LinesFromFile.Length; i++)
    {
      // StakeholdersFromHeader[i - 1] - ключ, получаемый из списка стейкхолдеров, можно было и через *.Keys.ToArray
      double currentLineSumm = LinesFromFile[i].Replace(".", ",").Split(" ").Sum(data => double.TryParse(data, out double number) ? number : 0);
      currentStakeholdersMap[StakeholdersFromHeader[i - 1]][coordinateIndex] = currentLineSumm;
    }
    return currentStakeholdersMap;
  }

  private static async Task<bool> WriteMostImportantStakeholdersToFileAsync(Dictionary<string, double[]> StakeholdersMap, string path, double minimumScoreAsCoordinate){
    try
    {
      // Производим фильтрацию по координатам, наши координаты должны быть (не включительно) больше минимально переданного рейтинга (половина количества стейкхолдеров в моем решении)
      var FilteredStackholders = StakeholdersMap.Where(stakeholder => stakeholder.Value[0] > minimumScoreAsCoordinate && stakeholder.Value[1] > minimumScoreAsCoordinate).ToDictionary();
      // Записываем ключи отфильтрованных пользователей в файл
      await File.WriteAllLinesAsync(path, FilteredStackholders.Keys.ToArray());
      return true;
    }
    catch (Exception ex){
      Console.WriteLine(@$"Что-то пошло не так! \n{ex.Message}");
      return false;
    }

  }
}