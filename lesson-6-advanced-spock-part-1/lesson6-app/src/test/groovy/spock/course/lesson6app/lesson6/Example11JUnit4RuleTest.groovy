package spock.course.lesson6app.lesson6

import org.junit.Rule
import org.junit.rules.DisableOnDebug
import org.junit.rules.TestName
import org.junit.rules.TestRule
import org.junit.rules.Timeout
import spock.lang.Specification

class Example11JUnit4RuleTest extends Specification {

    @Rule
    public TestName testName

    @Rule
    public final TestRule debugTimeout = new DisableOnDebug(Timeout.seconds(4))

//    @Rule
//    public final TestRule globalTimeout = Timeout.seconds(3)

    def "first test"() {
        expect:
        2 == 2
        Thread.sleep(2000)
        print(testName.methodName)
    }

    def "second test"() {
        expect:
        print(testName.methodName)
        Thread.sleep(3000)
        2 == 2
    }

    def "third test"() {
        expect:
        2 == 2
        print(testName.methodName)
    }
}
