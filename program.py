d = {}  # словарь для результата по каждому стейкхолдеру
# Чтение и обработка файла interests
with open("interest.txt", encoding=" UTF-8") as file_in:
    lines = file_in.readline().split(" | ")
    lines[-1] = lines[-1].rstrip()
    n = len(lines)
    matrix_interest = [x.rstrip() for x in file_in.readlines()]  # матрица интересов
# Чтение и обработка файла influence
with open("influence.txt", encoding=" UTF-8") as file_in:
    lines = file_in.readline().split(" | ")
    lines[-1] = lines[-1].rstrip()
    if n != len(lines):  # если число стейкхолдеров в обехи матрицах не равны -> ошибка ввода
        raise ValueError("Ошибка ввода")
    matrix_influence = [x.rstrip() for x in file_in.readlines()]  # матрица влияния
for i in range(len(matrix_interest)):
    summary = 0
    string = matrix_interest[i].split()
    for s in string:
        if s == "_":
            continue
        s = float(s)
        summary += s
    if summary > n / 2:  # если сумма входит во 2 половину графика -> в словаре по ключу с номером появляется значение True
        d[f"{i + 1}"] = True
for i in range(len(matrix_influence)):
    summary = 0
    string = matrix_influence[i].split()
    for s in string:
        if s == "_":
            continue
        s = float(s)
        summary += s
    if summary <= n/2:
        if f"{i + 1}" in d:
            del d[f"{i + 1}"]
# Запись результата
with open("result.txt", "w") as f:
    for k, v in d.items():
        i = 0
        if i == len(d) - 1:
            f.write(f"Stakeholder {k}")
        else:
            f.write(f"Stakeholder {k}\n")
        i += 1
