package spock.course.lesson7app.service

import spock.course.lesson7app.domain.AbstractMessage
import spock.course.lesson7app.domain.HomeworkMessage
import spock.course.lesson7app.enums.StageType
import spock.course.lesson7app.exception.SendException
import spock.course.lesson7app.service.examples.ExampleGenerator
import spock.lang.Specification

class Example4MockTest extends Specification {

    static final HOMEWORK_MESSAGE = ExampleGenerator.createHomeworkMessage()

    HomeworkProducerService homeworkProducerService

    ErrorHandlerService errorHandlerService = Mock {
        0 * send(_, _) >> { throw new SendException("test error") }
    }
    AuditService auditService = Mock {
        1 * sendToAudit(StageType.BEFORE_ACTION, HOMEWORK_MESSAGE)
        1 * sendToAudit(StageType.AFTER_ACTION, HOMEWORK_MESSAGE)
    }
    CollegeKafkaProducer collegeKafkaProducer = Mock {
        1 * send(HOMEWORK_MESSAGE)
    }

    def "send"() {
        given:
        def storageKafkaProducer = Mock(StorageKafkaProducer) {
            1 * send(HOMEWORK_MESSAGE)
        }
        homeworkProducerService = new HomeworkProducerService(errorHandlerService, collegeKafkaProducer, storageKafkaProducer, auditService)

        when:
        homeworkProducerService.sendHomework(HOMEWORK_MESSAGE as AbstractMessage<HomeworkMessage>)

        then:
        noExceptionThrown()
    }
}
