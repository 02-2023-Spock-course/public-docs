package spock.course.lesson7app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Student implements Serializable {

    private String name;

    private String surname;
}
