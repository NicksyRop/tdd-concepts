package Service;

import model.User;

/**
 * @author nnkipkorir
 * created 19/11/2024
 */

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleMailConfirmation(User user) {
        //put user details to email queue
        System.out.println("Email verification scheduled");
    }
}
