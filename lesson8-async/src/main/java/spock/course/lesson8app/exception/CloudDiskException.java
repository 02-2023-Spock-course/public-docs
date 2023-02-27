package spock.course.lesson8app.exception;

public class CloudDiskException extends RuntimeException {

    public CloudDiskException(Throwable cause) {
        super(cause);
    }
}
