package spock.course.badproject.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class Attribute {

    private String key;

    private String value;

    private String type;

    private boolean isReadOnly;
}
