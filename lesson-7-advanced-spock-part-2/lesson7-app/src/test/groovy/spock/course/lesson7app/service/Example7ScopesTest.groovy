package spock.course.lesson7app.service

import spock.course.lesson7app.domain.HomeworkMessage
import spock.course.lesson7app.enums.HomeworkType
import spock.lang.Specification

class Example7ScopesTest extends Specification {

    static final HOMEWORK_MESSAGE = new HomeworkMessage(homeworkType: HomeworkType.NEW_WORK)
    static final SECOND_HOMEWORK_MESSAGE = new HomeworkMessage(homeworkType: HomeworkType.CORRECTION_HOMEWORK)

    StorageKafkaProducer storageKafkaProducer = Mock()

    def "send"() {
        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)
        storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)

        then: "не проверяем последовательность"
        1 * storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)
        1 * storageKafkaProducer.send(HOMEWORK_MESSAGE)
        noExceptionThrown()
    }

    def "good send - version 1"() {
        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)
        storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)

        then: "проверяем последовательность"
        1 * storageKafkaProducer.send(HOMEWORK_MESSAGE)

        then:
        1 * storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)
        noExceptionThrown()
    }

    def "good send - version 2"() {
        when:
        storageKafkaProducer.send(HOMEWORK_MESSAGE)

        then:
        1 * storageKafkaProducer.send(HOMEWORK_MESSAGE)

        when:
        storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)

        then:
        1 * storageKafkaProducer.send(SECOND_HOMEWORK_MESSAGE)
        noExceptionThrown()
    }
}
