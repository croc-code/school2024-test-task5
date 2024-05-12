# проверить правильность программы

def get_matrix(filename: str):
    """
    Функия считывает файл и возвращает матрицу и имена стейкхолдеров
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
    matrix_interest, names_interest = get_matrix('interest.txt')
    matrix_influence, names_influence = get_matrix('influence.txt')

    ranks_interest = calculate_rank(matrix_interest)
    ranks_influence = calculate_rank(matrix_influence)
    
    is_best_dict = {}
    for names, ranks in [(names_interest, ranks_interest), (names_influence, ranks_influence)]:
        for i in range(len(ranks_interest)):
            name = names[i]
            rank = ranks[i]
            if name not in is_best_dict:
                is_best_dict[name] = True
            if rank <= 2.5:
                is_best_dict[name] = False

    best_stakeholders = []
    for stakeholder_name, is_best in is_best_dict.items():
        if is_best:
            best_stakeholders.append(stakeholder_name)

    return best_stakeholders


if __name__ == '__main__':
    best_stakeholders = get_most_valuable_stakeholders()
    with open('result.txt', 'w') as file:
        for stakeholder in best_stakeholders:
            file.write(stakeholder)
            file.write('\n')
