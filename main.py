def get_matrix(filename: str):
    with open(filename, 'r') as file:
        lines = file.readlines()
        names = lines[0].strip().split(' | ')
        matrix = []
        for line in lines[1:]:
            matrix.append([float(number) if number != '_' else 0.0 for number in line.strip().split()])
        return matrix, names


def calculate_rank(matrix):
    ranks = [sum(row) for row in matrix]
    return ranks


def get_most_valuable_stakeholders(ranks_interest, ranks_influence, names):
    res = []
    for i in range(len(ranks_interest)):
        if ranks_interest[i] > 2.5 and ranks_influence[i] > 2.5:
            res.append(names[i])
    return res


matrix_interest, names_interest = get_matrix('interest.txt')
matrix_influence, names_influence = get_matrix('influence.txt')

ranks_interest = calculate_rank(matrix_interest)
ranks_influence = calculate_rank(matrix_influence)

# get_most_valuable_stakeholders(ranks_interest, ranks_influence, names)

# for data, lines in zip([data_interest, data_influence], [lines_interest, lines_influence]):
#     n = len(lines[0])
#     for line in lines[1:]:
#         row = []
#         for n in line.strip().split():
#             if n != '_':
#                 n = float(n)
#             else:
#                 n = 0.0
#             row.append(n)
#         data.append(row)
#     data = np.array(data)
#
# print(data_interest)
# print(data_influence)
#
# n = len(data_interest)
#
# coords_interest = np.empty(n)
#
# data_interest[]
