package Service;

import model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName,
                           String lastName, String email,
                           String password, String repeatPassword) {

        if(StringUtils.isBlank(firstName)){
            throw new IllegalArgumentException("FirstName cannot be blank");
        }

        return new User(firstName, lastName, email, UUID.randomUUID().toString());
    }
}
