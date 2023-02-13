package spock.course.lesson5app.service

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.course.lesson5app.domain.StudyGroup
import spock.course.lesson5app.repository.StudyGroupRepository
import spock.lang.Specification

@ContextConfiguration(classes = StudyGroupService)
class StudyGroupServiceStubTest extends Specification {

    static final NAME = 'name1'
    static final STUDY_GROUP = new StudyGroup(name: NAME)

    @Autowired
    StudyGroupService studyGroupService

    @SpringBean
    StudyGroupRepository studyGroupRepository = Stub() {
        findFirstByName(NAME) >> Optional.of(STUDY_GROUP)
    }

    def "findStudyGroup"() {
        given:
        studyGroupRepository.findFirstByName(NAME) >> STUDY_GROUP

        when:
        def groupFromService = studyGroupService.findStudyGroup(NAME)

        then:
        groupFromService == STUDY_GROUP
        noExceptionThrown()
    }
}
