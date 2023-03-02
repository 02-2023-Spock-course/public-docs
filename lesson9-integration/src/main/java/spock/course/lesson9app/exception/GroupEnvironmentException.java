package spock.course.lesson9app.exception;

public class GroupEnvironmentException extends RuntimeException {

    public GroupEnvironmentException(String message) {
        super(String.format(message));
    }
}
