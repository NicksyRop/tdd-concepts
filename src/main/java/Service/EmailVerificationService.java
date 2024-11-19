package Service;

import model.User;

/**
 * @author nnkipkorir
 * created 19/11/2024
 */

public interface EmailVerificationService {
    void scheduleMailConfirmation(User user);
}
