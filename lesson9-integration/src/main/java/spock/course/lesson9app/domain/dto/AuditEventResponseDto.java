package spock.course.lesson9app.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spock.course.lesson9app.enums.AuditEventResponseType;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuditEventResponseDto implements Serializable {

    private AuditEventResponseType auditEventResponseType;

    private String message;
}
