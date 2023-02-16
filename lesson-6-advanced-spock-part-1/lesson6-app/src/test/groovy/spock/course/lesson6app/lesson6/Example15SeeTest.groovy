package spock.course.lesson6app.lesson6

import spock.lang.See
import spock.lang.Specification

@See([
        "http://wiki-page.ru/1.html",
        "http://wiki-page.ru/2.html"
])
class Example15SeeTest extends Specification {

    def "first test"() {
        expect:
        2 == 2
    }

    @See("http://wiki-page.ru/3.html")
    def "second test"() {
        expect:
        Thread.sleep(1000)
        2 == 2
    }

}
