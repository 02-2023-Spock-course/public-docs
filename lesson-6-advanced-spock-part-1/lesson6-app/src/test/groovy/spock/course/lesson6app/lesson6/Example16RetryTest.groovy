package spock.course.lesson6app.lesson6

import spock.lang.Retry
import spock.lang.Specification

/**
 * Применяют на сложных и поэтому нестабильных интеграционных тестах
 */
class Example16RetryTest extends Specification {

    def counter = 10

    def "first test"() {
        expect:
        2 == 2
    }

    @Retry(count = 20, delay = 10)
    def "second test"() {
        expect:
        Thread.sleep(100)
        if(counter-- > 0) {
            throw new RuntimeException("test error $counter")
        }
        2 == 2
    }

}
