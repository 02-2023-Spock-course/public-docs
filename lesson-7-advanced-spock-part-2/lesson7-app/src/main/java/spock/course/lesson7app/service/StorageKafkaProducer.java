package spock.course.lesson7app.service;

import spock.course.lesson7app.domain.AbstractMessage;
import spock.course.lesson7app.domain.HomeworkMessage;

public class StorageKafkaProducer {

    public void send(AbstractMessage<HomeworkMessage> message) {
        System.out.println("StorageKafkaProducer.send" + message);
        //TODO here my message sending
    }


}
