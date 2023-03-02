package spock.course.lesson9app.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudyGroupDto implements Serializable {

    private String name;

    private int studentCount;
}
