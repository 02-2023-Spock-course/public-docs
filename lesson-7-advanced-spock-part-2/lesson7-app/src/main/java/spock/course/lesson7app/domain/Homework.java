package spock.course.lesson7app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Homework implements Serializable {

    private Student student;

    private String number;

    private String createDateTime;

    private byte[] data;
}
