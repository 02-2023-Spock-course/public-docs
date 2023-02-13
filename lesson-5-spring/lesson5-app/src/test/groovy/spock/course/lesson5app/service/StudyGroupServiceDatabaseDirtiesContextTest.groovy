package spock.course.lesson5app.service

import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.annotation.DirtiesContext
import spock.course.lesson5app.repository.StudyGroupRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import javax.transaction.Transactional
import java.util.random.RandomGenerator

@Transactional
@SpringBootTest
//задаём последовательность выполнения в чётко указанном порядке
@Stepwise
//Инициализируем SpringContext на каждый метод
// или на конкретный метод - очень медленно работает, но иногда нужно!
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudyGroupServiceDatabaseDirtiesContextTest extends Specification {

    //    @Value("\${spring.datasource.url}")
    @Value('${spring.datasource.url}')
    String dbUrl

    @Value('${spring.datasource.driverClassName}')
    String dbDriver

    @Value('${spring.datasource.username}')
    String username

    @Value('${spring.datasource.password}')
    String password

    @Shared Sql sql

    @Autowired
    StudyGroupService studyGroupService

    @Autowired
    StudyGroupRepository studyGroupRepository

    def setup() {
        sql = Sql.newInstance(dbUrl, username, password, dbDriver)
    }

    def cleanup() {
        sql.close()
    }

    def "addStudyGroup"() {
        given:
        def id1 = RandomGenerator.getDefault().nextLong()
        def name1 = 'group1'
        def name2 = 'group2'
        sql.execute("INSERT INTO study_group (id, name) VALUES ($id1, '$name1')")

        when:
        def id2 = studyGroupService.addStudyGroup(name2)
        var group2 = studyGroupRepository.getReferenceById(id2)
        var group1 = studyGroupRepository.getReferenceById(id1)

        then:
        id2
        id1 != id2
        group1 != group2
        group1.name == name1
        group2.name == name2
        noExceptionThrown()
    }

    def "addStudyGroup - protect from double group names"() {
        given:
        def name = "group1"
        sql.execute("INSERT INTO study_group (id, name) VALUES (${RandomGenerator.getDefault().nextInt()}, '$name')")

        when:
        def id = studyGroupService.addStudyGroup(name)

        then:
        thrown(DataIntegrityViolationException)
    }
}
