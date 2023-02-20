package spock.course.lesson7app.exception;

public class SendException extends RuntimeException {

    public SendException(Throwable cause) {
        super(cause);
    }
}
