package spock.course.badproject.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class AttributeGroup {

    private String name;

    private List<Attribute> attributes;

}
