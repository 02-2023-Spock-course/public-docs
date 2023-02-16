package spock.course.lesson6app.lesson6

import spock.lang.PendingFeature
import spock.lang.PendingFeatureIf
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

class Example6TimeoutTest extends Specification {

    @Timeout(5)
    def "first test"() {
        expect:
        2 == 2
    }

    //будущий функционал. Игнорим ошибки, но если тест прошёл, то наоборот ошибка!
    @PendingFeature
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    def "second test"() {
        expect:
        Thread.sleep(5000)
        2 == 2
    }

    @PendingFeatureIf({ os.windows })
    def "third test"() {
        expect:
        2 == 3
    }

    def "four test"() {
        expect:
        2 == 2
    }
}
