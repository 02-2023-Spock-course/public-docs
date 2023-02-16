package spock.course.lesson6app.lesson6

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Test with title")
@Narrative("""
As user
I want create two StudyGroup 
""")
class Example8TitleTest extends Specification {

    def "first test"() {
        expect:
        2 == 2
    }

}
