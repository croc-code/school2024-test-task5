
class Solution:
    """
    Класс, который содержит метод по нахождению самых важных стейкхолдеров
    """
    @staticmethod
    def get_most_important(ranks_1, ranks_2) -> list[str]:
        """
        Метод по нахождению самых важных стейкхолдеров по двум осям: Интерес и Влияние
        :param ranks_1: Список кортежей вида tuple(<наименование ЗС>, <ранг>)
        :param ranks_2: Список кортежей вида tuple(<наименование ЗС>, <ранг>)
        :return: Список самых важных стейкхолдеров в произвольном порядке
        """
        assert len(ranks_1) == len(ranks_2), "Lists ranks_1 and ranks_2 most have the same length!"

        chosen_names_by_rank1: set[str] = set()
        half_len = len(ranks_1) / 2
        for name, rank in ranks_1:
            if rank >= half_len:
                chosen_names_by_rank1.add(name)

        most_important: list[str] = []
        for name, rank in ranks_2:
            if name in chosen_names_by_rank1:
                most_important.append(name)

        return most_important


class Table:
    """
    Класс, который будет создавать объекты для удобного хранения входных данных - матриц
    """
    def __init__(self, names: list[str], table: list[list[float]]):
        """

        :param names: Список наименований ЗС
        :param table: Матрица (список списков типа float)
        """
        self.names: list[str] = names
        self.table: list[list[float]] = table

    def get_ranks(self) -> list[tuple[str, float]]:
        """
        Метод для нахождения рангов, соответствующих наименованиям ЗС
        :return: Список кортежей вида tuple(<наименование ЗС>, <ранг>)
        """
        return [(name, sum(row)) for name, row in zip(self.names, self.table)]

    def __str__(self):
        """
        Магические метод для удобного вывода в терминал объекта типа Table через print(...)
        """
        lst: list[str] = ["Names: "]
        lst.append(", ".join(self.names))
        lst.append("\nTable:\n")
        for row in self.table:
            for el in row:
                lst.append(str(el).rjust(5, " "))
            lst.append("\n")
        return "".join(lst)


def read_matrix_file(file_name: str) -> Table:
    """
    Функция для чтения матриц из заданного файла
    :param file_name: Файл с заданным форматом ввода матрицы
    :return: Объект типа Table
    """
    with open(file_name, 'r') as file:
        names: list[str] = [name.strip() for name in file.readline().split("|")]

        table: list[list[float]] = []
        for _ in range(len(names)):
            row = file.readline().split()
            table.append([
                float(el) if el != '_' else 0
                for el in row
            ])

        return Table(names, table)


if __name__ == "__main__":
    interest = read_matrix_file("interest.txt")
    print("Interest Matrix:\n", interest, sep="")

    influence = read_matrix_file("influence.txt")
    print("Influence Matrix:\n", influence, sep="")

    ranks_1 = interest.get_ranks()
    ranks_2 = influence.get_ranks()
    # print(ranks_1, ranks_2, sep='\n')

    most_important = Solution.get_most_important(ranks_1, ranks_2)
    print("Most important:", *most_important)
    with open("result.txt", 'w') as result_file:
        for name in most_important:
            result_file.write(name + "\n")
