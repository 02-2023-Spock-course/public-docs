package spock.course.lesson6app.lesson6

import spock.course.lesson6app.examples.ExampleConst
import spock.course.lesson6app.repository.StudyGroupRepository
import spock.course.lesson6app.service.StudyGroupService
import spock.lang.Specification

import static spock.course.lesson6app.examples.ExampleGenerator.createStudyGroup

/**
 * выносим типовую генерацию domain-примеров для тестов в отдельные классы
 */
class Example1Test extends Specification {

    static final GROUP_NAME = ExampleConst.GROUP_NAME
    static final STUDY_GROUP = createStudyGroup()

    def "findStudyGroup"() {
        given:
        def studyGroupRepository = Mock(StudyGroupRepository)
        def studyGroupService = new StudyGroupService(studyGroupRepository)
        studyGroupRepository.findFirstByName(GROUP_NAME) >> Optional.of(STUDY_GROUP)
        studyGroupService = new StudyGroupService(studyGroupRepository)

        when:
        def groupFromService = studyGroupService.findStudyGroup(GROUP_NAME)

        then:
        groupFromService
        groupFromService == STUDY_GROUP
        groupFromService.students.size() == 2
        noExceptionThrown()
    }
}
