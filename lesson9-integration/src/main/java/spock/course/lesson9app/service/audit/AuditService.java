package spock.course.lesson9app.service.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spock.course.lesson9app.domain.dto.AuditEventDto;
import spock.course.lesson9app.domain.dto.AuditEventResponseDto;
import spock.course.lesson9app.exception.AuditException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditServiceClient auditServiceClient;

    public AuditEventResponseDto sendToAudit(AuditEventDto auditEventDto) {
        var response = auditServiceClient.addEvent(auditEventDto);
        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            throw new AuditException(getMessage(response));
        }
        return response.getBody();
    }

    private String getMessage(ResponseEntity<AuditEventResponseDto> response) {
        return Optional.ofNullable(response.getBody())
                .map(AuditEventResponseDto::getMessage)
                .orElse("");
    }
}
