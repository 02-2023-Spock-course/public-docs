package spock.course.lesson7app.service;

import spock.course.lesson7app.domain.AbstractMessage;
import spock.course.lesson7app.domain.HomeworkMessage;

public class CollegeKafkaProducer {

    public void send(AbstractMessage<HomeworkMessage> message) {
        System.out.println("CollegeKafkaProducer.send" + message);
        //TODO here my message sending
    }


}
