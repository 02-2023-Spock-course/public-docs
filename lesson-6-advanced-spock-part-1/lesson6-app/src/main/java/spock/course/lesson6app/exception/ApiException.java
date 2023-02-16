package spock.course.lesson6app.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message, String message1) {
        super(String.format(message, message1));
    }
}
