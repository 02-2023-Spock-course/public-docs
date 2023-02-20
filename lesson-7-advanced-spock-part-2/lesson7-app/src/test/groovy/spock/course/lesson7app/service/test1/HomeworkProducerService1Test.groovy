package spock.course.lesson7app.service.test1

import spock.course.lesson7app.domain.AbstractMessage
import spock.course.lesson7app.domain.HomeworkMessage
import spock.course.lesson7app.enums.StageType
import spock.course.lesson7app.exception.SendException
import spock.course.lesson7app.service.AuditService
import spock.course.lesson7app.service.CollegeKafkaProducer
import spock.course.lesson7app.service.ErrorHandlerService
import spock.course.lesson7app.service.HomeworkProducerService
import spock.course.lesson7app.service.StorageKafkaProducer
import spock.course.lesson7app.service.examples.ExampleGenerator
import spock.lang.Specification

class HomeworkProducerService1Test extends Specification {

    static final HOMEWORK_MESSAGE = ExampleGenerator.createHomeworkMessage()

    HomeworkProducerService homeworkProducerService
    ErrorHandlerService errorHandlerService
    CollegeKafkaProducer collegeKafkaProducer
    StorageKafkaProducer storageKafkaProducer
    AuditService auditService

    def "send via mocks"() {
        given:
        errorHandlerService = Mock()
        collegeKafkaProducer = Mock()
        storageKafkaProducer = Mock()
        auditService = Mock()
        homeworkProducerService = new HomeworkProducerService(errorHandlerService, collegeKafkaProducer, storageKafkaProducer, auditService)

        when:
        homeworkProducerService.sendHomework(HOMEWORK_MESSAGE as AbstractMessage<HomeworkMessage>)

        then:
        0 * errorHandlerService.send(*_)
        1 * collegeKafkaProducer.send(*_)
        1 * storageKafkaProducer.send(*_)
        2 * auditService.sendToAudit(*_)
        noExceptionThrown()
    }

    def "send via mocks with exception and use errorHandlerService"() {
        given:
        errorHandlerService = Mock()
        collegeKafkaProducer = Mock()
        storageKafkaProducer = Mock()
        auditService = Mock()
        homeworkProducerService = new HomeworkProducerService(errorHandlerService, collegeKafkaProducer, storageKafkaProducer, auditService)

        when:
        homeworkProducerService.sendHomework(HOMEWORK_MESSAGE as AbstractMessage<HomeworkMessage>)

        then:
        1 * errorHandlerService.send(_, _)
        1 * collegeKafkaProducer.send(*_) >> { throw new SendException("test error") }
        0 * storageKafkaProducer.send(*_)
        2 * auditService.sendToAudit(*_)
        thrown(SendException)
    }

    def "send via stubs"() {
        given:
        def sentMessage = null
        errorHandlerService = Stub()
        collegeKafkaProducer = Stub()
        storageKafkaProducer = Stub()
        auditService = Stub()
        storageKafkaProducer.send(_) >> {
            sentMessage = it[0]
            null
        }
        errorHandlerService.send(_ as Exception, _ as AbstractMessage) >> { throw new SendException("test error") }
        homeworkProducerService = new HomeworkProducerService(errorHandlerService, collegeKafkaProducer, storageKafkaProducer, auditService)

        when:
        homeworkProducerService.sendHomework(HOMEWORK_MESSAGE as AbstractMessage<HomeworkMessage>)

        then:
        sentMessage
        sentMessage == HOMEWORK_MESSAGE
        noExceptionThrown()
    }

    def "send"() {
        given:
        errorHandlerService = Mock()
        collegeKafkaProducer = Mock()
        storageKafkaProducer = Mock()
        auditService = Mock()
        homeworkProducerService = new HomeworkProducerService(errorHandlerService, collegeKafkaProducer, storageKafkaProducer, auditService)

        when:
        homeworkProducerService.sendHomework(HOMEWORK_MESSAGE as AbstractMessage<HomeworkMessage>)

        then:
        1 * collegeKafkaProducer.send(HOMEWORK_MESSAGE)
        1 * storageKafkaProducer.send(HOMEWORK_MESSAGE)
        0 * errorHandlerService.send(_, _) >> { throw new SendException("test error") }
        1 * auditService.sendToAudit(StageType.BEFORE_ACTION, HOMEWORK_MESSAGE)
        1 * auditService.sendToAudit(StageType.AFTER_ACTION, HOMEWORK_MESSAGE)
        noExceptionThrown()
    }
}
