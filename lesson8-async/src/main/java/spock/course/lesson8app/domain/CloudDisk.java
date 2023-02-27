package spock.course.lesson8app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CloudDisk implements Serializable {

    private String name;

    private int gbSize;

    private boolean isReserved;
}
