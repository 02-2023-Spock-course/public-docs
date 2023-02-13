package spock.course.lesson5app.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message, String message1) {
        super(String.format(message, message1));
    }
}
