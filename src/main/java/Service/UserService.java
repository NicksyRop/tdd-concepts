package Service;

import model.User;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
