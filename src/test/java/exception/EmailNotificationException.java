package exception;

/**
 * @author nnkipkorir
 * created 19/11/2024
 */

public class EmailNotificationException extends RuntimeException {
    public EmailNotificationException(String message) {
        super(message);
    }
}
