package spock.course.lesson9app.service.audit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spock.course.lesson9app.domain.dto.AuditEventDto;
import spock.course.lesson9app.domain.dto.AuditEventResponseDto;

@FeignClient(name = "${audit.service.name}", url = "${audit.service.url}")
public interface AuditServiceClient {

    @PostMapping(path = "/audit/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AuditEventResponseDto> addEvent(@RequestBody AuditEventDto auditEventDto);
}
