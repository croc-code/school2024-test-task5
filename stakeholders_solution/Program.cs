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

    // 
    string pathToInterestFile = Path.Combine(AppContext.BaseDirectory, @"..\..\..\..\StakeholdersData\interest.txt");
    string pathToInfluenceFile = Path.Combine(AppContext.BaseDirectory, @"..\..\..\..\StakeholdersData\influence.txt");

    StakeholdersMap = await GetUpdatedStakeholdersMapFromFileAsync(StakeholdersMap, pathToInterestFile, 0);
    StakeholdersMap = await GetUpdatedStakeholdersMapFromFileAsync(StakeholdersMap, pathToInfluenceFile, 1);
    
    for (int i = 0; i < StakeholdersMap.Keys.ToArray().Length; i++)
    {
      // Debug console
      Console.WriteLine();
      Console.WriteLine(StakeholdersMap.Keys.ToArray()[i] + " => " + StakeholdersMap[StakeholdersMap.Keys.ToArray()[i]][0] + " : " + StakeholdersMap[StakeholdersMap.Keys.ToArray()[i]][1]);
      // 
    }
  }

  private static async Task<Dictionary<string, double[]>> GetUpdatedStakeholdersMapFromFileAsync(Dictionary<string, double[]> currentStakeholdersMap, string path, int coordinateIndex){
    // Читаем асинхронно все линии из файла, 
    // чтобы наше приложение не зависало, пока считывает файл
    string[] LinesFromFile = await File.ReadAllLinesAsync(path);

    // Первую линию сплиттим по символу '|', получаем массив стейкхолдеров
    string[] StakeHoldersFromHeader = LinesFromFile[0].Split('|');

    // Добавляем имена стейкхолдеров как значения в наш словарь
    for (int i = 0; i < StakeHoldersFromHeader.Length; i++)
    {
      StakeHoldersFromHeader[i] = StakeHoldersFromHeader[i].TrimStart().TrimEnd();
      if (!currentStakeholdersMap.ContainsKey(StakeHoldersFromHeader[i]))
      {
        currentStakeholdersMap.Add(StakeHoldersFromHeader[i], new double[2]);
      }
    }

    // Присваеваем значение интереса ( X ) к ключу ( Стейкхолдеру )
    for (int i = 1; i < LinesFromFile.Length; i++)
    {
      double currentLineSumm = LinesFromFile[i].Replace(".", ",").Split(" ").Sum(data => double.TryParse(data, out double number) ? number : 0);
      currentStakeholdersMap[StakeHoldersFromHeader[i - 1]][coordinateIndex] = currentLineSumm;

      // Debug console
      Console.WriteLine(LinesFromFile[i]);
      Console.WriteLine(currentStakeholdersMap.Keys.ToArray()[i - 1] + ":" + currentStakeholdersMap[StakeHoldersFromHeader[i - 1]][coordinateIndex]);
      // 
    }
    return currentStakeholdersMap;
  }
}