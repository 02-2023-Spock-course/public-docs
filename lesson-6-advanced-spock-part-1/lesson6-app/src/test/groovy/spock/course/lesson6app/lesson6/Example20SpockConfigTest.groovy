package spock.course.lesson6app.lesson6

import spock.lang.Ignore
import spock.lang.Specification

/**
 * lesson6-app/src/test/resources/spock.course.lesson6app.SpockConfig.groovy
 */
class Example20SpockConfigTest extends Specification {

    @Ignore
    def "first test"() {
        expect:
        2 == 2
        throw new RuntimeException("Hello Spock!")
    }

    def "second test"() {
        expect:
        2 == 2
    }
}
