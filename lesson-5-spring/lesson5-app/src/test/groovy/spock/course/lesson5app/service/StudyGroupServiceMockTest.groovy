package spock.course.lesson5app.service

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.course.lesson5app.domain.StudyGroup
import spock.course.lesson5app.repository.StudyGroupRepository
import spock.lang.Specification

@ContextConfiguration(classes = StudyGroupService)
class StudyGroupServiceMockTest extends Specification {

    @Autowired
    StudyGroupService studyGroupService

    @SpringBean
    StudyGroupRepository studyGroupRepository = Mock()

    def "addStudyGroup"() {
        given:
        def name = 'name1'

        when:
        studyGroupService.addStudyGroup(name)

        then:
        1 * studyGroupRepository.saveAndFlush(_) >> new StudyGroup(name: name)
    }
}
