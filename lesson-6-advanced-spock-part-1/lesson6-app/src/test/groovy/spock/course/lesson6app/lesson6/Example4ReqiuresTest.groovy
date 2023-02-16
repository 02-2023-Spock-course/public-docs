package spock.course.lesson6app.lesson6


import spock.lang.Requires
import spock.lang.Specification

class Example4ReqiuresTest extends Specification {

    @Requires({ os.linux })
    def "linux test"() {
        expect:
        2 == 2
    }

    def "second test"() {
        expect:
        2 == 2
    }

    def "third test"() {
        expect:
        2 == 2
    }
}
