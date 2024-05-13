def read_matrix(filename): 
    with open(filename, 'r') as f:
        stakeholders = f.readline().strip().split('|')
        matrix = []
        for line in f:
            row = []
            for value in line.strip().split():
                if value == '_':
                    row.append(0)
                else:
                    row.append(float(value))
            matrix.append(row)
    return stakeholders, matrix

def analyze_stakeholders(interest, influence):
    interest_stakeholders, interest_matrix = read_matrix(interest) #считывание матрицы интереса
    influence_stakeholders, influence_matrix = read_matrix(influence) #считывание матрицы влияния
    
    if interest_stakeholders!= influence_stakeholders:
        raise ValueError("Stakeholders do not match between interest and influence matrices")
        
    stakeholders = []
    for i in range(len(interest_stakeholders)): #создание массива кортежей с информацией о каждом стейкхолдере
        stakeholders.append((sum(interest_matrix[i]), sum(influence_matrix[i]), interest_stakeholders[i]))
    
    stakeholders.sort(key=lambda x: (x[0], x[1]), reverse=True) #сортировка массива кортежей с информацией о каждом стейкхолдере
    
    with open('result.txt', 'w') as f:
        i = 0
        while stakeholders[i][0] > (len(stakeholders) / 2.0) and stakeholders[i][1] > (len(stakeholders) / 2.0):
            f.write(stakeholders[i][2] + '\n') #добавление важных стейкхолдеров в результирующий файл
            i += 1

analyze_stakeholders('interest.txt', 'influence.txt')