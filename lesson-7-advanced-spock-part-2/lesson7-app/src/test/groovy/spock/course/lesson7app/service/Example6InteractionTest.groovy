package spock.course.lesson7app.service


import spock.course.lesson7app.service.examples.ExampleGenerator
import spock.lang.Specification

class Example6InteractionTest extends Specification {

    static final HOMEWORK_MESSAGE = ExampleGenerator.createHomeworkMessage()

    StorageKafkaProducer storageKafkaProducer = Mock()

    def "correct test with send"() {
        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)

        then:
        interaction {
            def expectedMessage = HOMEWORK_MESSAGE
            1 * storageKafkaProducer.send(expectedMessage)
        }
        noExceptionThrown()
    }
}
