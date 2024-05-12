def get_matrix(filename: str):
    """
    Функция для считывания файлов interest.txt и influence.txt

    Parameters
    ----------
    filename : путь к файлу

    Returns
    -------
    Матрица и list с именами стейкхолдеров
    """
    with open(filename, 'r') as file:
        lines = file.readlines()
        names = lines[0].strip().split(' | ')
        matrix = []
        for line in lines[1:]:
            matrix.append([float(number) if number != '_' else 0.0 for number in line.strip().split()])
        return matrix, names


def calculate_rank(matrix):
    """
    Функция расчитывает ранк пользователей по матрице

    ranks[i] — ранк i-го пользователя
    """
    ranks = [sum(row) for row in matrix]
    return ranks


def get_most_valuable_stakeholders():
    """
    Функция для нахождения наиболее важных стейкхолдеров
    """
    # получение матриц
    matrix_interest, names_interest = get_matrix('interest.txt')
    matrix_influence, names_influence = get_matrix('influence.txt')

    # получение листов с ранками
    ranks_interest = calculate_rank(matrix_interest)
    ranks_influence = calculate_rank(matrix_influence)

    # cередина горизонтальной и вертекальных осей, которая будет разделять плоскость на 4 квадрата
    middle = len(names_interest) / 2

    is_best_dict = {}  # ключ - имя стейкхолдера, значение - является ли данный стейкхолдер наиболее ценным
    for names, ranks in [(names_interest, ranks_interest), (names_influence, ranks_influence)]:
        for i in range(len(ranks_interest)):  # идем по матрицам интереса и влияния
            name = names[i]
            rank = ranks[i]
            if name not in is_best_dict:  # по умолчанию всех стейкхолдеров считаем наиболее ценными
                is_best_dict[name] = True
            if rank < middle:  # но если ранг интереса или влияния ниже middle, то считаем этого стейкхолдера НЕ наиболее ценным
                is_best_dict[name] = False

    best_stakeholders = []
    for stakeholder_name, is_best in is_best_dict.items():  # получаем лист из наиболее ценных стейкхолдеров
        if is_best:
            best_stakeholders.append(stakeholder_name)

    return best_stakeholders


if __name__ == '__main__':
    best_stakeholders = get_most_valuable_stakeholders()
    with open('result.txt', 'w') as file:
        for stakeholder in best_stakeholders:
            file.write(stakeholder)
            file.write('\n')
