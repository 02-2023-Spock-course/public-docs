package spock.course.lesson6app.lesson6

import spock.lang.Specification
import spock.util.mop.Use

@Use(Example7ListExtension)
class Example7UseExtensionTest extends Specification {

    def "avg test"() {
        expect:
        [2,4].avg() == 3
        [1,1].avg() == 1
    }

    @Use(Example7ListExtension)
    def "size test"() {
        expect:
        ![1,2,3,4,5,6,7,8].isEmpty()
        [1,2,3].isEmpty()
    }
}
