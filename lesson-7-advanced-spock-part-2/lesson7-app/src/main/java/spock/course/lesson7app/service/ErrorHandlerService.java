package spock.course.lesson7app.service;

import lombok.RequiredArgsConstructor;
import spock.course.lesson7app.domain.AbstractMessage;

import java.util.List;

@RequiredArgsConstructor
public class ErrorHandlerService {

    private final ErrorHandlerKafkaProducer errorHandlerKafkaProducer;

    public void send(Exception e, AbstractMessage message) {
        errorHandlerKafkaProducer.send(message);
    }

    public int getInt() {
        return 123;
    }

    public String getString() {
        return "string";
    }

    public List<String> getList() {
        return List.of("string1", "string2");
    }
}
