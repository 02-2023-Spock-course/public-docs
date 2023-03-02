package spock.course.lesson9app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spock.course.lesson9app.domain.dto.AuditEventDto;
import spock.course.lesson9app.domain.dto.StudyGroupDto;
import spock.course.lesson9app.domain.StudyGroupEntity;
import spock.course.lesson9app.enums.AuditEventType;
import spock.course.lesson9app.exception.ApiException;
import spock.course.lesson9app.repository.StudyGroupRepository;
import spock.course.lesson9app.service.audit.AuditService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StudyGroupService {

    private static final int DISK_GB_SIZE = 100;
    private static final int CPU_SIZE = 8;
    private static final int RAM_SIZE = 32;

    private final StudyGroupRepository studyGroupRepository;
    private final GroupEnvironmentService groupEnvironmentService;
    private final AuditService auditService;

    public void addStudyGroup(StudyGroupDto studyGroupDto) throws InterruptedException, ExecutionException {
        log.info("Prepare for create {}", studyGroupDto);
        var studyGroup = new StudyGroupEntity();
        studyGroup.setName(studyGroupDto.getName());
        studyGroup.setStudentCount(studyGroupDto.getStudentCount());
        groupEnvironmentService.reserveGroupEnvironment(studyGroupDto.getStudentCount(), CPU_SIZE, RAM_SIZE, DISK_GB_SIZE);
        var studyGroupEntity = studyGroupRepository.saveAndFlush(studyGroup);
        var eventDto = createAuditEventDto(AuditEventType.CREATE, studyGroupEntity.toString());
        auditService.sendToAudit(eventDto);
        log.info("Successfully created {}", studyGroupEntity);
    }

    public void deleteStudyGroup(String name) {
        var group = findStudyGroup(name);
        studyGroupRepository.deleteById(group.getId());
    }

    public StudyGroupEntity findStudyGroup(String name) {
        return studyGroupRepository.findFirstByName(name)
                .orElseThrow(() -> new ApiException("Not found group with name: %s", name));
    }

    public StudyGroupDto getStudyGroupDto(String name) {
        var entity = findStudyGroup(name);
        var dto = new StudyGroupDto();
        dto.setName(entity.getName());
        dto.setStudentCount(entity.getStudentCount());
        return dto;
    }

    private AuditEventDto createAuditEventDto(AuditEventType auditEventType, String data) {
        var dto = new AuditEventDto();
        dto.setAuditEventType(auditEventType);
        dto.setData(data);
        return dto;
    }
}
