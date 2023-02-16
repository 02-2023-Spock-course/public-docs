package spock.course.lesson6app.lesson6

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class Example5StepwiseTest extends Specification {

    def "first test"() {
        expect:
        2 == 2
    }

    @Ignore
    def "second test"() {
        expect:
        2 == 3
    }

    def "third test"() {
        expect:
        2 == 2
    }

    def "four test"() {
        expect:
        2 == 2
    }
}
