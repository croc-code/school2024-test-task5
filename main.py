class Program:
    def __init__(self):
        self.dots = {}  # Словарь точек
        self.mid = 0.0  # Центр оси
        self.max_dots = [0, 0]  # Максимальные точки для вывода
        self.read_files()
        self.flag = sum(i >= self.mid for i in self.max_dots)

    def read_files(self):  # Функция для чтения файлов и находения итоговых рангов
        with open('interest.txt', 'r', encoding='utf-8') as interest, open('influence.txt', 'r',
                                                                           encoding='utf-8') as influence:
            interest_file = interest.read()
            influence_file = influence.read()
            j = 0
            for i, k in zip(interest_file.split('\n')[1:], influence_file.split('\n')[1:]):
                x, y = sum(list(map(float, i.replace('_', '0').split()))), sum(
                    list(map(float, k.replace('_', '0').split())))
                self.dots[interest_file.split('\n')[0].split('|')[j].strip()] = [x, y]
                self.max_dots[0] = max(self.max_dots[0], x)
                self.max_dots[1] = max(self.max_dots[1], y)
                j += 1
            self.mid = j / 2

    def write_file(self):  # Создание файла и запись в него результата
        file = open('result.txt', 'w', encoding='utf-8')
        for key in self.dots:
            x, y = self.dots[key]
            if x >= self.mid and y >= self.mid and self.flag == 2:
                file.write(key + '\n')
            elif (x >= self.mid or y >= self.mid) and self.flag == 1:
                file.write(key + '\n')
            elif self.flag == 0:
                file.write(key + '\n')
        file.close()


if __name__ == '__main__':  # Точка запуска
    app = Program()
    app.write_file()
