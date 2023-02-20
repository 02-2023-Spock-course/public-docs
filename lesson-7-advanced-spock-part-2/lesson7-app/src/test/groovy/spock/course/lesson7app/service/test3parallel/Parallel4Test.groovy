package spock.course.lesson7app.service.test3parallel

import spock.lang.Specification

//[INFO] Total time:  13.422 s

class Parallel4Test extends Specification {


    def "test"() {
        expect:
        Thread.sleep(5000)
    }

}
