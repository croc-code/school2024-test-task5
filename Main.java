package org.example;
import java.io.*;
import java.lang.System;
import java.util.*;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        //входные данные
        File interestFile = new File("src/main/resources/interest.txt");
        File influenceFile = new File("src/main/resources/influence.txt");

        //создание итогового файла с результатом
        File result = new File("src/main/resources/result.txt");
        try {
            result.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Данные для решния задачи
        List<Double> ratingInterest = new ArrayList<>();
        List<Double> ratingInfluence = new ArrayList<>();
        //сумма рейтинга для каждого stakeHolder'а
        double sumInterest = 0.0;
        double sumInfluence = 0.0;
        //соот. номера stakeHolder'а и его суммой рейтинга
        Map<Integer, Double> mapInterest = new HashMap<>();
        Map<Integer, Double> mapInfluence = new HashMap<>();
        //для текстового вывода результата
        StringBuilder resultIn = new StringBuilder();
        StringBuilder resultOut = new StringBuilder();

        //основное решение задачи (в данном случае используется файл interest.txt в соот. с примером)
        try(InputStream inputStream = new FileInputStream(interestFile)) {
            int a = inputStream.read();
            while (a!=-1){ //считываение всех символов входного файла
                resultIn.append((char)a);
                a = inputStream.read();
            }

            //Построчное разбиение в соотвествии с условиями задачи
            String[] strings = resultIn.toString().split("\\n");
            String[] stackHolders = strings[0].split("\\|");

            //INTEREST (формирование результата по параметру INTEREST (горизонтальное суммирование по матрице))
            for (int i = 1; i<strings.length; i++) { //первую строку не учитываем т.к. там номера стейкхолдеров, а не данные для расчетов
                String[] elements = strings[i].split(" ");
                for (int j = 0; j < elements.length; j++) {
                    if (elements[j].equals("_")) {
                        sumInterest += 0;
                    } else {
                        sumInterest += Double.parseDouble(elements[j]);
                    }
                    if (j+1 == stackHolders.length) {
                        ratingInterest.add(sumInterest);
                        mapInterest.put(i, sumInterest);
                        sumInterest = 0.0;
                    }
                }
            }
            //ФОРМИРОВАНИЕ ИТОГОВОГО РЕЗУЛЬТАТА; Выводим ключи, соответствующие отсортированным значениям (максимальным)
            finalResultForm(ratingInterest, mapInterest, stackHolders, resultOut);
            resultOut.append("\n"); //разделение итогов interest и influence

            //INFLUENCE ("вертикальное" суммирование по матрице)
            for (int k = 0; k < stackHolders.length; k++){ //двигаемся диагонально
                for (int l = 1; l < strings.length; l++) { //двигаемся вертикально (не учитываем строку с stakeHolder номерами)
                    try {
                        sumInfluence += Double.parseDouble(strings[l].split(" ")[k]);
                    }
                    catch (NumberFormatException e){
                        //e.printStackTrace();
                    }
                    if (l == stackHolders.length){
                        ratingInfluence.add(sumInfluence);
                        mapInfluence.put(k+1, sumInfluence);
                        sumInfluence = 0.0;
                    }
                }
            }
            //ФОРМИРОВАНИЕ ИТОГОВОГО РЕЗУЛЬТАТА; Выводим ключи, соответствующие отсортированным значениям (максимальным)
            finalResultForm(ratingInfluence, mapInfluence, stackHolders, resultOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ЗАПИСЬ В ИТОГОВЫЙ ФАЙЛ RESULT.TXT
        try(OutputStream outputStream = new FileOutputStream(result)) {
            //Преобразуем строки результатов в массив байтов
            byte[] bytes = resultOut.toString().getBytes();
            //Записываем байты в файл
            outputStream.write(bytes);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Для вывода двух самых значимых stakeHolder
    public static void finalResultForm(List<Double> selectedList, Map<Integer, Double> selectedMap, String[] stackHolders, StringBuilder resultOut){
        Collections.sort(selectedList);
        for (var entry : selectedMap.entrySet()) {
            if (entry.getValue().equals(selectedList.get(stackHolders.length-1))) {
                resultOut.append("Stakeholder " + entry.getKey().toString() + "\n");
                continue;
            }
            if (entry.getValue().equals(selectedList.get(stackHolders.length-2))) {
                resultOut.append("Stakeholder " + entry.getKey().toString() + "\n");
            }
        }
    }
}
