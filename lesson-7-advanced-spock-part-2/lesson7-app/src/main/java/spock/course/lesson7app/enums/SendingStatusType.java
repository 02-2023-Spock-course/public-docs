package spock.course.lesson7app.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SendingStatusType {

    SUCCESSFULLY_SENT("Успешно отправлено"),
    ERROR_SENT("Ошибка отправки");

    private String name;
}
