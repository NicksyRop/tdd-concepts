package exception;

/**
 * @author nnkipkorir
 * created 17/11/2024
 */

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
