package spock.course.lesson7app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import spock.course.lesson7app.enums.HomeworkType;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HomeworkMessage extends AbstractMessage<Homework> {

    private HomeworkType homeworkType;
}
