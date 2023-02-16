package spock.course.lesson6app.lesson6


import spock.course.lesson6app.service.StudentService
import spock.course.lesson6app.service.StudyGroupService
import spock.lang.Specification
import spock.lang.Subject

@Subject([StudentService, StudyGroupService])
class Example10SubjectTest extends Specification {

    def "first test"() {
        given:
        def studentService = new StudentService()
        def studyGroupService = new StudyGroupService(null)

        expect:
        2 == 2
    }
}
