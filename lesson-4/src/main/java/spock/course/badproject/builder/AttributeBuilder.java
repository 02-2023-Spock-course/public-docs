package spock.course.badproject.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spock.course.badproject.domain.Attribute;
import spock.course.badproject.dto.in.AttributeDto;
import spock.course.badproject.exception.AuditException;
import spock.course.badproject.service.AuditService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AttributeBuilder {

    private static final String ERROR_MESSAGE = "Audit failed for attribute %s !";

    private final AuditService auditService;

    public List<Attribute> buildListWithFilter(List<AttributeDto> srcAttributeList, String filterGroupName) {
        return Optional.ofNullable(srcAttributeList).orElseGet(ArrayList::new).stream()
                .filter(srcAttributeDto -> srcAttributeDto.getGroupName().equals(filterGroupName))
                .map(this::build)
                .collect(Collectors.toList());
    }

    public Attribute build(AttributeDto srcAttribute) {
        var attribute = Attribute.builder()
                .key(srcAttribute.getKey())
                .value(srcAttribute.getValue())
                .type(srcAttribute.getType())
                .build();
        var isAudited = auditService.sendAttributeToAudit(attribute);
        if(!isAudited) {
            log.error(String.format(ERROR_MESSAGE, attribute));
            throw new AuditException(String.format(ERROR_MESSAGE, attribute));
        }
        return attribute;
    }
}
