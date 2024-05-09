import pandas as pd


def get_ratings(filename: str) -> dict:
    with open(filename, 'r') as f:
        headers = f.readline().strip().split(" | ")

    df = pd.read_csv(filename, delimiter=" ", skiprows=1, header=None, na_values='_', keep_default_na=False)
    df.columns = headers
    df.index = headers

    df['sum'] = df.sum(axis=1)

    return df['sum'].to_dict()


interest = get_ratings('interest.txt')
influence = get_ratings('influence.txt')

# значение границы для осей ограничивающих правый верхний квадрант
edge = len(interest) / 2

# получаем стейкхолдеров, находящихся в правом верхнем квадранте
stakeholders = {stakeholder: (influence[stakeholder], interest[stakeholder]) for stakeholder in interest
                if influence[stakeholder] >= edge and interest[stakeholder] >= edge}


# записываем полученных стейкхолдеров в файл
with open('result.txt', 'w') as file:
    for stakeholder in stakeholders:
        file.write(stakeholder + "\n")
