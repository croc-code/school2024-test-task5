import sys


def main():
    # Выбор входных файлов
    if len(sys.argv) == 3:
        interest_file = sys.argv[1]
        influence_file = sys.argv[2]
    else:
        interest_file = "interest.txt"
        influence_file = "influence.txt"

    # Чтение входных файлов и выбор имен стейкхолдеров и матрицы сравнения
    interest_name, interest_data = readfile(interest_file)
    influence_name, influence_data = readfile(influence_file)
    # Проверка на соответствие имен стейкхолдеров во входных файлах
    if interest_name != influence_name:
        print("Different Stakeholder names in files")
        exit()

    # Счет итогового ранга для каждого стейкхолдера (координат на графике влияние/интерес)
    interest_ranks = getranks(interest_data)
    influence_ranks = getranks(influence_data)

    # Выбор всех стейкхолдеров, чьи координаты лежат в правом верхнем квадранте и запись их имен в итоговый файл
    writefile("result.txt", getresult(interest_name, interest_ranks, influence_ranks))


def readfile(filename: str):  # Функция открытия указанного файла и возвращения имен стейкхолдеров и матрицы сравнения
    try:
        # Открытие файла на чтение и чтение содержимого построчно
        file = open(filename, 'r')
        lines = file.readlines()
        # Разделение первой строки с именами по разделителю
        stakeholder_names = lines[0].strip().split(' | ')
        # Чтение матрицы сравнения в двумерный массив чисел с плавающей точной, с заменой символа '_' на 0.0
        data = []
        for line in lines[1:]:
            data.append([])
            for num in line.strip().split():
                data[-1].append(0.0 if num == '_' else float(num))
        return stakeholder_names, data
    # Обработка некоторых возможных ошибок при открытии файла
    except FileNotFoundError:
        print(f"The input file with the given name \"{filename}\" was not found")
        exit()
    except PermissionError:
        print(f"Cannot access the specified file \"{filename}\"")
        exit()
    except Exception as e:
        print(f"An error has occurred: {e} with file \"{filename}\"")
        exit()


def getranks(data: list):  # Функция счета итогового ранга для каждого стейкхолдера (сумма элементов списка)
    ranks = []
    for i in range(len(data)):
        ranks.append(sum(data[i]))
    return ranks


# Функция выбора всех стейкхолдеров, чьи координаты лежат в правом верхнем квадранте
def getresult(interest_name: list[str], interest_ranks: list, influence_ranks: list):
    result = []
    # Проверка условия, что координаты стейкхолдера лежат в правом верхнем квадранте
    for i in range(len(interest_ranks)):
        if interest_ranks[i] >= len(interest_ranks) / 2 and influence_ranks[i] >= len(interest_ranks) / 2:
            result.append(interest_name[i])
    return result


def writefile(filename: str, data: list):  # Функция записи полученного результата в файл
    file = open(filename, 'w')
    for val in data:
        file.write(val + "\n" if val != data[-1] else val)


if __name__ == '__main__':
    main()
