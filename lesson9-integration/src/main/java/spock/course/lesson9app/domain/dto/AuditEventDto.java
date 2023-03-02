package spock.course.lesson9app.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spock.course.lesson9app.enums.AuditEventType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuditEventDto implements Serializable {

    private AuditEventType auditEventType;

    private String data;

    private LocalDateTime eventDateTime;

}
