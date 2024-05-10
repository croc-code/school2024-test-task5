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

    // Читаем асинхронно все линии из файла, 
    // чтобы наше приложение не зависало, пока считывает файл
    string[] LinesFromInterest = await File.ReadAllLinesAsync(pathToInterestFile);

    // Первую линию сплиттим по символу '|', получаем массив стейкхолдеров
    string[] StakeHoldersFromHeader = LinesFromInterest[0].Split('|');

    // Добавляем имена стейкхолдеров как значения в наш словарь
    for (int i = 0; i < StakeHoldersFromHeader.Length; i++)
    {
      if (!StakeholdersMap.ContainsKey(StakeHoldersFromHeader[i]))
      {
        StakeholdersMap.Add(StakeHoldersFromHeader[i], new double[2]);
      }
    }

    // Присваеваем значение интереса ( X ) к ключу ( Стейкхолдеру )
    for (int i = 1; i < LinesFromInterest.Length; i++)
    {
      double currentLineSumm = LinesFromInterest[i].Replace(".", ",").Split(" ").Sum(data => double.TryParse(data, out double number) ? number : 0);
      StakeholdersMap[StakeHoldersFromHeader[i - 1]][0] = currentLineSumm;

      // Debug console
      Console.WriteLine(LinesFromInterest[i]);
      Console.WriteLine(StakeholdersMap.Keys.ToArray()[i - 1] + ":" + StakeholdersMap[StakeHoldersFromHeader[i - 1]][0]);
      // 
    }

  }
}