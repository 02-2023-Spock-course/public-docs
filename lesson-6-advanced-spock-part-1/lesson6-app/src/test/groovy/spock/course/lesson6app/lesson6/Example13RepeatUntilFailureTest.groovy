package spock.course.lesson6app.lesson6

import spock.lang.RepeatUntilFailure
import spock.lang.Specification

class Example13RepeatUntilFailureTest extends Specification {

    @RepeatUntilFailure(maxAttempts = 100)
    def "first test"() {
        expect:
        2 == 2
    }

    def "second test"() {
        expect:
        Thread.sleep(1000)
        2 == 2
    }

    def "third test"() {
        expect:
        2 == 2
    }
}
