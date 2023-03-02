package spock.course.lesson9app.exception;

public class CloudDiskException extends RuntimeException {

    public CloudDiskException(Throwable cause) {
        super(cause);
    }
}
