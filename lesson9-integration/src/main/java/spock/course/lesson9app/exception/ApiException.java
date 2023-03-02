package spock.course.lesson9app.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message, String message1) {
        super(String.format(message, message1));
    }
}
