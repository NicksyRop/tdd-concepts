package Service;

import model.User;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName,
                           String lastName, String email,
                           String password, String repeatPassword) {

        return new User(firstName);
    }
}
