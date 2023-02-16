package spock.course.lesson6app.lesson6

import spock.lang.Specification
import spock.lang.Tag

/**
 * maven-surefire-plugin
 * <excludedGroups>IntegrationTest</excludedGroups>
 */
@Tag("IntegrationTest")
class Example12TagTest extends Specification {

    def "first test"() {
        expect:
        2 == 2
    }

    @Tag("UnitTest")
    def "third test"() {
        expect:
        2 == 2
    }

    def "second test"() {
        expect:
        Thread.sleep(1000)
        2 == 2
    }
}
