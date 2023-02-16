package spock.course.lesson6app.lesson6


import spock.lang.IgnoreIf
import spock.lang.IgnoreRest
import spock.lang.Specification
import spock.util.environment.Jvm
import spock.util.environment.OperatingSystem

/**
 * @Ignore
 *
 * To make predicates easier to read and write, the following properties are available inside the closure:

 sys A map of all system properties

 env A map of all environment variables

 os Information about the operating system (see spock.util.environment.OperatingSystem)

 jvm Information about the JVM (see spock.util.environment.Jvm)
 */
class Example3IgnoreTest extends Specification {

    @IgnoreRest
//    @Ignore("потому что гладиолус")
    //@IgnoreIf({ System.getProperty("os.name").containsIgnoreCase("windows") })
    //@IgnoreIf({ os.linux })
//    @IgnoreIf({ os.isLinux() }) //spock.util.environment.OperatingSystem
//    @IgnoreIf({ Jvm.getCurrent().isJava19Compatible() || OperatingSystem.getCurrent().isWindows() })
    //spock.util.environment.OperatingSystem
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
