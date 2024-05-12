import matplotlib.pyplot as plt
from main import get_matrix, calculate_rank


def show_interest_influence_plot(ranks_interest, names_interest, ranks_influence, names_influence):
    """
    Функция для отрисовки итоговых точек на графике для визуальной проверки решения
    """

    def get_sorted_ranks(ranks, names):
        """
        Вспомогательная функция для получения отсортированых по именам стейкхолдеров рангов
        """
        sorted_ranks = []
        for i in range(len(names)):
            sorted_ranks.append([names[i], ranks[i]])
        sorted_ranks.sort(key=lambda x: x[0])
        return sorted_ranks

    size = len(names_interest)
    middle = size / 2

    # получение x и y координат точек
    x, y = [], []
    for name, val in get_sorted_ranks(ranks_interest, names_interest):
        x.append(val)
    for name, val in get_sorted_ranks(ranks_influence, names_influence):
        y.append(val)

    # отрисовка графика
    plt.plot(x, y, 'ro')
    plt.axvline(x=middle, color='r', linestyle='--')
    plt.axhline(y=middle, color='r', linestyle='--')
    plt.xticks(list(range(0, size + 1)))
    plt.yticks(list(range(0, size + 1)))
    plt.show()


if __name__ == '__main__':
    matrix_interest, names_interest = get_matrix('interest.txt')
    matrix_influence, names_influence = get_matrix('influence.txt')

    ranks_interest = calculate_rank(matrix_interest)
    ranks_influence = calculate_rank(matrix_influence)

    # функция для визуального тестирования правильности ответа
    show_interest_influence_plot(ranks_interest, names_interest, ranks_influence, names_influence)
