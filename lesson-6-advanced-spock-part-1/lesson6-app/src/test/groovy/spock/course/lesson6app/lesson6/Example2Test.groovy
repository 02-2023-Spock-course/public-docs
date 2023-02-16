package spock.course.lesson6app.lesson6

import spock.course.lesson6app.examples.ExampleConst
import spock.course.lesson6app.repository.StudyGroupRepository
import spock.course.lesson6app.service.StudyGroupService
import spock.lang.Specification

import static spock.course.lesson6app.examples.ExampleGenerator.createStudyGroup

/**
 * обращаемся к переданным параметрам в Mock через closures
 */
class Example2Test extends Specification {

    static final GROUP_NAME = ExampleConst.GROUP_NAME
    static final STUDY_GROUP = createStudyGroup()

    def "findStudyGroup"() {
        given:
        def studyGroupRepository = Mock(StudyGroupRepository)
        def studyGroupService = new StudyGroupService(studyGroupRepository)
        def nameFromRepoServiceParam = null

        when:
        def groupFromService = studyGroupService.findStudyGroup(GROUP_NAME)

        then:
        1 * studyGroupRepository.findFirstByName(GROUP_NAME) >> {
            nameFromRepoServiceParam = it[0]
            return Optional.of(STUDY_GROUP)
        }
        nameFromRepoServiceParam
        nameFromRepoServiceParam == GROUP_NAME
        groupFromService
        groupFromService == STUDY_GROUP
        groupFromService.students.size() == 2
        noExceptionThrown()
    }
}
