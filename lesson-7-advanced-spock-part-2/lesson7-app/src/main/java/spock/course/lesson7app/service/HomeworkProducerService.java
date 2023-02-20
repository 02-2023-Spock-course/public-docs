package spock.course.lesson7app.service;

import lombok.RequiredArgsConstructor;
import spock.course.lesson7app.domain.AbstractMessage;
import spock.course.lesson7app.domain.HomeworkMessage;
import spock.course.lesson7app.enums.StageType;
import spock.course.lesson7app.exception.SendException;

@RequiredArgsConstructor
public class HomeworkProducerService {

    private final ErrorHandlerService errorHandlerService;
    private final CollegeKafkaProducer collegeKafkaProducer;
    private final StorageKafkaProducer storageKafkaProducer;
    private final AuditService auditService;

    public void sendHomework(AbstractMessage<HomeworkMessage> message) {
        try {
            auditService.sendToAudit(StageType.BEFORE_ACTION, message);
            collegeKafkaProducer.send(message);
            storageKafkaProducer.send(message);
        } catch (Exception e) {
            errorHandlerService.send(e, message);
            throw new SendException(e);
        } finally {
            auditService.sendToAudit(StageType.AFTER_ACTION, message);
        }
    }
}
