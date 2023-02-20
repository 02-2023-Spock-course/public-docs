package spock.course.lesson7app.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HomeworkType {

    NEW_WORK("Новая работа"),
    CORRECTION_HOMEWORK("Исправленная работа"),
    APPROVED_HOMEWORK("Согласованная работа");

    private String name;
}
