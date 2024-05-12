def read_matrix(file_name):
    with open(file_name, 'r') as file:
        lines = file.readlines()
        stakeholders = lines[0].strip().split('|')
        matrix = [[float(value) if value != '_' else 0 for value in line.strip().split()[1:]] for line in lines[1:]]
    return stakeholders, matrix


def calculate_sum(matrix):
    return [sum(row) for row in matrix]


def main():
    interest_stakeholders, interest_matrix = read_matrix('interest.txt')
    influence_stakeholders, influence_matrix = read_matrix('influence.txt')

    interest_sum = calculate_sum(interest_matrix)
    influence_sum = calculate_sum(influence_matrix)

    result_stakeholders = [stakeholder for stakeholder, interest, influence in zip(interest_stakeholders, interest_sum, influence_sum) if interest + influence >= 3]

    with open('result.txt', 'w') as file:
        file.write('\n'.join(result_stakeholders))


if __name__ == "__main__":
    main()
