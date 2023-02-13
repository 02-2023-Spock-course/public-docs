package spock.course.lesson5app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spock.course.lesson5app.domain.StudyGroup;
import spock.course.lesson5app.exception.ApiException;
import spock.course.lesson5app.repository.StudyGroupRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    public Long addStudyGroup(String name) {
        var studyGroup = new StudyGroup();
        studyGroup.setName(name);
        return studyGroupRepository.saveAndFlush(studyGroup).getId();
    }

    public void deleteStudyGroup(String name) {
        var group = findStudyGroup(name);
        studyGroupRepository.deleteById(group.getId());
    }

    public StudyGroup findStudyGroup(String name) {
        return studyGroupRepository.findFirstByName(name)
                .orElseThrow(() -> new ApiException("Not found group with name: %s", name));
    }
}
