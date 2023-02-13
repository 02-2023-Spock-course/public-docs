package spock.course.lesson5app.service

import spock.course.lesson5app.domain.StudyGroup
import spock.course.lesson5app.repository.StudyGroupRepository
import spock.lang.Specification

class StudyGroupServiceNoSpringTest extends Specification {

    static final NAME = 'name1'
    static final STUDY_GROUP = new StudyGroup(name: NAME)

    StudyGroupService studyGroupService

    StudyGroupRepository studyGroupRepository = Stub() {
        findFirstByName(NAME) >> Optional.of(STUDY_GROUP)
    }

    def setup() {
        studyGroupService = new StudyGroupService(studyGroupRepository)
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
