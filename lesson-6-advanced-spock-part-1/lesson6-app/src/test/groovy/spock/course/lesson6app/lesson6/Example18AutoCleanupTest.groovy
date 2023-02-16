package spock.course.lesson6app.lesson6

import spock.lang.AutoCleanup
import spock.lang.Specification

class Example18AutoCleanupTest extends Specification {

    static final NAME = "Ivan"

    @AutoCleanup(quiet = true, value = "closeAfterWork")
    MyClass myClass

    def setup() {
        myClass = new MyClass(name: NAME)
    }

    def "first test"() {
        expect:
        myClass.name == NAME
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

class MyClass {//implements AutoCloseable

    String name

    void closeAfterWork() throws Exception {
        this.name = null
        throw new RuntimeException("test error!")
    }
}