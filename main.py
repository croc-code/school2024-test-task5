def max_sum_index_def(matrix): #Считает максимальную сумму элементов строк и выводит индекс строки с максимальной суммой элементов
    max_sum_index = max((sum(row), index) for index, row in enumerate(matrix))[1]
    return max_sum_index

with open('interest.txt', 'r') as file1:
    lines = file1.readlines()
    first_line = lines[0].strip().split() #Записывает в отдельный массив первую строку из файла
    matrix_int = [list(map(lambda x: 0 if x == '_' else float(x), line.strip().split())) for line in lines[1:]]

with open('influence.txt', 'r') as file2:
    lines = file2.readlines()[1:] #Отрезает первую строку
    matrix_inf = [list(map(lambda x: 0 if x == '_' else float(x), line.strip().split())) for line in lines]

index_int = max_sum_index_def(matrix_int) #Используем функцию для матрицы из файла "interest.txt"
index_inf = max_sum_index_def(matrix_inf) #Используем функцию для матрицы из файла "influence.txt"

with open('result.txt', 'w') as output_file: #Для необходимого вывода умножаем найденные индексы на 3
    output_file.write(' '.join(first_line[index_int * 3 : index_int * 3 + 2]) + '\n')
    output_file.write(' '.join(first_line[index_inf * 3 : index_inf * 3 + 2]))
