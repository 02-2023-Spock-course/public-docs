package spock.course.lesson6app.lesson6

import spock.lang.Specification
import spock.lang.Stepwise
import spock.util.environment.OperatingSystem
import spock.util.environment.RestoreSystemProperties

@Stepwise
class Example17RestoreSystemPropertiesTest extends Specification {

    @RestoreSystemProperties
    def "first test"() {
        given:
        System.setProperty('os.name', 'Linux')

        expect:
        OperatingSystem.current.family == OperatingSystem.Family.LINUX
    }

    def "second test"() {
        expect:
        OperatingSystem.current.family == OperatingSystem.Family.WINDOWS
    }

    def "third test"() {
        expect:
        OperatingSystem.current.family == OperatingSystem.Family.WINDOWS
    }
}
