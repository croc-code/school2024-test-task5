

def readFromFile(filename: str): # чтение матрицы из файла 
    try:
        with open(filename, 'r') as file:
            lines = file.readlines()
            header = lines[0].strip().split(' | ')
            data = [[0 if x == '_' else float(x) for x in line.strip().split()] for line in lines[1:]]
        return header, data
    except FileNotFoundError:
        print("The specified file does not exist.")
    except PermissionError:
        print("You do not have permission to access the file.")
    except Exception as e:
        print(f"An error has occurred: {e}")
    
def importantStakeholdersIdxs(data:list[list[float | int]]) -> list[int]:
    return [1 if sum(line)>len(data)/2.0 else 0 for line in data]
 
def importantStakeholders(interestIdx:list[int], influenceIdx:list[int])->list[int]: # поиск необходимых Stakeholder`ов
    interest=importantStakeholdersIdxs(interestIdx)
    influence=importantStakeholdersIdxs(influenceIdx)
    res=[]
    for i in range(len(interest)):
        if interest[i]==1 and influence[i]==1:
            res.append(i)
    return res
            
def writeToFile(filename: str, data:list[int]) -> None: # запись результата в файл
    with open(filename, 'w') as f:
        for i, stakeholder in enumerate(data):
            f.write(f"Stakeholder {stakeholder+1}\n")
def main():
    header, dataInt = readFromFile('interest.txt')
    _, dataInf = readFromFile('influence.txt')
    impStakeholders=importantStakeholders(interestIdx=dataInt, influenceIdx=dataInf)
    writeToFile("result.txt", impStakeholders)
    
if __name__ == "__main__":
    main()