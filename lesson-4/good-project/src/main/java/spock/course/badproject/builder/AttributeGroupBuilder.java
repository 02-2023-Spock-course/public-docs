package spock.course.badproject.builder;

import lombok.AllArgsConstructor;
import spock.course.badproject.dto.in.AttributeDto;
import spock.course.badproject.domain.AttributeGroup;

import java.util.List;

@AllArgsConstructor
public class AttributeGroupBuilder {

    private final AttributeBuilder attributeBuilder;

    public List<AttributeGroup> build(List<AttributeDto> inAttributes) {
        return inAttributes.stream()
            .map(AttributeDto::getGroupName)
            .distinct()
            .map(group -> AttributeGroup.builder()
                    .name(group)
                    .attributes(attributeBuilder.buildListWithFilter(inAttributes, group))
                    .build())
                .toList();
    }
}
