import java.io.File

// Функция для вычисления рангов
fun calculateRanks(data: List<String>): MutableList<Double> {
    val ranks = mutableListOf<Double>()
    for (i in 1..<data.size) {
        var sum = 0.0
        data[i].split(" ").forEach {
                s -> s.toDoubleOrNull()?.let { sum += it }
        }
        ranks.add(sum)
    }
    return ranks
}

fun main(args: Array<String>) {
    // Путь до файла interest.txt
    lateinit var interestPath: String
    // Путь до файла influence.txt
    lateinit var influencePath: String
    // Путь до файла result.txt
    lateinit var resultPath: String
    when (args.size) {
        0 -> {
            interestPath = "interest.txt"
            influencePath = "influence.txt"
            resultPath = "result.txt"
        }
        3 -> {
            interestPath = args[0]
            influencePath = args[1]
            resultPath = args[2]
        }
        else -> {
            System.err.println("Неверное количество аргументов")
            return
        }
    }
    // Читаем данные из файлов
    val interest = File(interestPath).readLines()
    val influence = File(influencePath).readLines()
    // Список имен стейкхолдеров
    val names = interest[0].split(" | ")
    // Таблица рангов для интереса
    val interestRanks = calculateRanks(interest)
    // Таблица рангов для влияния
    val influenceRanks = calculateRanks(influence)
    val answer = StringBuilder()
    for (i in names.indices) {
        // Определяем,какие стейкхолдеры находятся в правом верхнем квадранте
        if (interestRanks[i] * 2 >= names.size && influenceRanks[i] * 2 >= names.size) {
            answer.append(names[i] + '\n')
        }
    }
    // Записываем результат в файл
    File(resultPath).writeText(answer.toString())
}