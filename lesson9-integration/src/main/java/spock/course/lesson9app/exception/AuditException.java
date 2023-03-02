package spock.course.lesson9app.exception;

public class AuditException extends RuntimeException {

    public AuditException(String message) {
        super(message);
    }
}
