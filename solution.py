def read_from_file(file_name):
  with open(file_name, 'r') as file:
    score_table = []
    # первая строчка (строка стейкхолдеров) считывается отдельно от таблицы сравнений
    for i, line in enumerate(file.readlines()):
      if i == 0:
        stakeholders = line.rstrip().split(" | ")
        continue
      score_table.append(line.rstrip().split(" "))
      score_table[i - 1][i - 1] = "0"                 # вместо "_" выставляем "0" чтобы не влияло на сумму
    return stakeholders, score_table

def write_to_file(stakeholders_list, file_name):
  with open(file_name, 'w') as file:
    for item in stakeholders_list:
      file.write(f"{item}\n")

def get_important_stakeholder_by_criteria(stakeholders, score_table):
  important_stakeholders = []
  mid = len(score_table[0]) / 2
  for i, stakeholder in enumerate(score_table):
    # преобразование строк "0.5" в число float 0.5 и проверка выше ли медианы
    if sum([float(i) for i in stakeholder]) > mid:
      # если сумма чисел в строке больше середины - значит важный
      important_stakeholders.append(stakeholders[i])
  return important_stakeholders

def main():
  stakeholders, interest_scores = read_from_file("interest.txt")
  stakeholders, influence_scores = read_from_file("influence.txt")

  interest_stakeholers = get_important_stakeholder_by_criteria(stakeholders, interest_scores)
  influence_stakeholders = get_important_stakeholder_by_criteria(stakeholders, influence_scores)
  
  # с помощью set выявляются уникальные элементы двух списков
  important_stakeholders = list(set(interest_stakeholers).intersection(influence_stakeholders))
  
  write_to_file(important_stakeholders, "result.txt")

if __name__ == "__main__":
  main()
