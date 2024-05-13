package ru.croc.stakeholder;

import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;

// Класс работы с квадрантами
public class QuadrantSeparator {
    public record Point(String name, double x, double y) {
    }


    // Общий метод получения нужных точек
    public static List<String> getRelevantNames(Collection<Point> points,
                                                BiPredicate<Double,
                                                        Double> predicate) {
        return points.stream().filter(point -> predicate.test(point.x(),
                                                              point.y()))
                     .map(Point::name).toList();
    }

    // Получение точек правого верхнего квадранта с границей по половине от
    // количества стейкхолдеров
    public static List<String> getMostImportant(Collection<Point> points) {
        return getRelevantNames(
                points,
                (x, y) -> x >= (double) points.size() / 2
                          && y >= (double) points.size() / 2);
    }
}
