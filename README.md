# Тестовое задание для отбора на Летнюю ИТ-школу КРОК по разработке

## Условие задания
В теории управления проектами существует такое понятие как стейкхолдер или, иначе говоря, заинтересованная сторона (ЗС). Это лицо, которое может иметь влияние на ход проекта и с чьим мнением нужно считаться по совершенно разным причинам.

Тем не менее, очевидно, что каждый из стейкхолдеров по-своему важен для проекта, что является следствием разного уровня показателей интереса и влияния, потому реакция на обратную связь от каждого из стейкхолдеров определяется в соответствии с матрицей заинтересованных лиц, которая позволяет наглядно определить наиболее и наименее весомых из них.

Чаще всего такая матрица представляет собой график с осями X и Y, где по оси X располагается величина влияния, а по оси Y - величина интереса, и так как значения величин - сугубо неотрицательные числа, работа идет в правом верхнем квадранте. В свою очередь такой срез оси координат делится на 4 части в центрах осей.

Рассмотрим на примере. Допустим, у нас есть 5 ЗС. Первым делом построим ось координат XY и нанесем деления по обеим осям от 0 до 5. Далее поделим пополам каждую из осей (это будут точки (0; 2.5) и (2.5; 0)) и построим пунктирную линию для разделения матрицы на 4 квадранта. Таким образом, мы получим будущую матрицу стейкхолдеров, на которой сможем визуально расположить их по степени влияния и интереса.
![Матрица стейкхолдеров](https://github.com/croc-code/school2024-test-task5/blob/master/stakeholders_matrix.png)

Для того, чтобы определить положение стейкхолдера, используется метод попарного сравнения, схожий с рейтинговыми таблицами в спорте. Строятся матрицы (для интереса и влияния отдельно), в строках и столбцах которых располагаются стейкхолдеры в одинаковом порядке следования, а на пересечении строк и столбцов выставляются значения 0, 0.5 и 1 по следующим правилам:
- 1, если стейкхолдер в текущей строке имеет большее влияние/интерес, чем стейкхолдер в текущем столбце;
- 0.5, если стейкхолдеры в текущих строке и столбце имеют одинаковое влияние/интерес;
- 0, если стейкхолдер в текущей строке имеет меньшее влияние/интерес, чем стейкхолдер в текущем столбце.

По диагоналям значения не выставляются, так как сравнивать стейкхолдера с самим собой некорректно.

Далее полученные величины в строках складываются и получается итоговый ранг заинтересованной стороны. То же самое, но наглядно:
![alt text](https://github.com/croc-code/school2024-test-task5/blob/master/pair_compair.png)

Построив такие матрицы для интереса и влияния, мы получаем “координаты” каждого из стейкхолдеров в матрице и можем расположить в соответствующих квадратах.
Самыми важными для проекта ЗС будут те, что располагаются в правом верхнем квадранте, а те, чье мнение не обязательно учитывать здесь и сейчас, - в левом нижнем.

Вам необходимо реализовать программу, которая по полученным данным матриц влияния и интереса будет определять самых важных стейкхолдеров. В качестве входных данных используются два текстовых файла:
- interest.txt (матрица попарного сравнения стейкхолдеров по уровню интереса)
- influence.txt (матрица попарного сравнения стейкхолдеров по уровню влияния)

Каждый из файлов имеет следующий формат:
- в первой строке файла располагаются наименования заинтересованных сторон, разделенные символов “|”, соответствующие наименованию строк и столбцов матрицы в одинаковом порядке следования;
- последующие строки представляют собой матрицу попарного сравнения со значениями 0, 0.5, 1 в соответствии с правилами формирования;
- на главной диагонали (сравнение ЗС самой с собой) вместо значения идет символ “_”.

Пример содержимого одного из входных файлов:
```
Stakeholder 1 | Stakeholder 2 | Stakeholder 3 | Stakeholder 4 | Stakeholder 5
_ 0 1 0 0
1 _ 0.5 1 1
0 0.5 _ 0 0.5
1 0 1 _ 1
1 0 0.5 0 _
```

В результате работы программа должна сформировать файл (result.txt), в котором будут располагаться самые важные стейкхолдеры (в произвольном порядке). Пример содержимого выходного файла:
```
Stakeholder 1
Stakeholder 3
```

## Автор решения
Удалых Максим Антонович

## Описание реализации
Решение выполнено на языке C#.

Для выполнения задачи производится построчное считывание строк из файлов `interest.txt` и `influence.txt`.

После чего, зная, что `величины в строках складываются и получается итоговый ранг заинтересованной стороны`, после получения строк из файла, нужно суммировать каждую строку значений (0, 0.5, 1) и закрепить данную сумму за стейкхолдером. C этой задачей отлично справится `Dictionary`, в ключ передаем имя нашего стейкхолдера, а в значение добавляем полученную из всей строки матрицы c индексом нашего стейкхолдера сумму (которая является рангом и одновременно координатой заинтересованности/влиятельности стейкхолдера) полученную из файла.

Далее, после формирования Словаря `<Стейкхолдер, {Влияние, Интерес}>` найдем таких стейкхолдеров, которые удовлетворяют условию `Влияние > половины количества стейкхолдеров && Интерес > половины количества стейкхолдеров`.


Добавлены проверки на существование файлов (хотя по сути это брошенный Exception).

Проверка на совпадение кол-ва стейкхолдеров между файлами и соотношение стейкхолдеров к ширине/высоте матрицы отсуствует, так как принял для себя условие всегда верных форматов файлов.

Хотел дополнить, почему реализация через Dictionary, а не создание класса Stakehoders со свойствами Name, Interest, Influence и методами для их изменения - как мне показалось, слишком мало связей между объектами для создания отдельных классов под сущность стейкхолдера и для решения задачи данного типа действительно лучше сработает Dictionary

## Инструкция по сборке и запуску решения

### Компиляция
#### 1. Скачать репозиторий

Скачать архив исходников можно через кнопку скачивания в ZIP архиве 

Или командой
``` $ git clone https://github.com/croc-code/school2024-test-task5.git```

#### 2. Проверить, что у вас стоит .NET SDK для запуска dotnet приложений
[Скачать .net 8 sdk можно по этой ссылке](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)

#### 3. Перейти в папку с проектом и запустить его

Запустить приложение можно используя команду `dotnet run` в вашем терминале или открыв решение в Visual Studio и нажав кнопку `Debug`

> При первом запуске приложение скажет, что не нашло файлов и это нормально, при первом запуске оно создает папку `StakeholdersData`. Папка `StakeholdersData` в проекте - тестовая, от туда можно взять данные для проверки работоспособности приложения.

После первого выполнения / билда программы будет создана папка с готовым исполняемым файлом `stakeholders_solution.exe` в папке `./bin/Debug/net8.0`.
Также там находится папка `StakeholdersData`, где должны храниться файлы influence.txt, interest.txt и result.txt

*(Возможно перед этим стоит разархивировать папку, если был выбран вариант скачивания в архиве)*

### Скомпилированное решение
> К сожалению мне не удалось сделать релиз с тегом в данном форке