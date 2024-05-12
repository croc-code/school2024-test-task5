package ru.croc.stakeholder;

/**
 * Класс представления заинтересованной стороны с полями:
 * Имя; Значение интереса; Значение влияния.
 */
public record Stakeholder(String name, double interest, double influence) {
}
