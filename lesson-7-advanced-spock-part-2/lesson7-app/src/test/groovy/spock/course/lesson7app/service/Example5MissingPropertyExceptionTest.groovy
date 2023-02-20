package spock.course.lesson7app.service


import spock.course.lesson7app.service.examples.ExampleGenerator
import spock.lang.Specification

/**
 * MissingPropertyException
 */
class Example5MissingPropertyExceptionTest extends Specification {

    static final HOMEWORK_MESSAGE = ExampleGenerator.createHomeworkMessage()

    StorageKafkaProducer storageKafkaProducer = Mock()

    def "incorrect Spock test - send"() {
        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)

        then:
        def expectedMessage = HOMEWORK_MESSAGE // в блоке then: нельзя объявлять переменные
//        1 * storageKafkaProducer.send(expectedMessage)
        noExceptionThrown()
    }

    def "correct test with send"() {
        given:
        def expectedMessage = HOMEWORK_MESSAGE

        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)

        then:
        1 * storageKafkaProducer.send(expectedMessage)
        noExceptionThrown()
    }
}
