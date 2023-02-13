package spock.course.lesson5app.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.course.lesson5app.domain.StudyGroup
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class StudyGroupRepositoryTest extends Specification {

    @Autowired
    StudyGroupRepository studyGroupRepository

    def "addStudyGroup"() {
        given:
        def name = 'group1'

        when:
        def groupAfterSave = studyGroupRepository.saveAndFlush(new StudyGroup(name: name))
        def groupAfterFind = studyGroupRepository.findFirstByName(name).get()

        then:
        groupAfterSave
        groupAfterFind
        groupAfterSave == groupAfterFind
        groupAfterFind.name == name
        noExceptionThrown()
    }
}
