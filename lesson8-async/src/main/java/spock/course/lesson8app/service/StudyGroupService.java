package spock.course.lesson8app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spock.course.lesson8app.domain.StudyGroupDto;
import spock.course.lesson8app.domain.StudyGroupEntity;
import spock.course.lesson8app.exception.ApiException;
import spock.course.lesson8app.repository.StudyGroupRepository;

import javax.transaction.Transactional;
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

    public void addStudyGroup(StudyGroupDto studyGroupDto) throws InterruptedException, ExecutionException {
        log.info("Prepare for create {}", studyGroupDto);
        var studyGroup = new StudyGroupEntity();
        studyGroup.setName(studyGroupDto.getName());
        groupEnvironmentService.reserveGroupEnvironment(studyGroupDto.getStudentCount(), CPU_SIZE, RAM_SIZE, DISK_GB_SIZE);
        var studyGroupEntity = studyGroupRepository.saveAndFlush(studyGroup);
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
}
