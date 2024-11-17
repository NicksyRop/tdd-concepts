package Service;

import model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static Utility.Constants.empty_first_name;
import static Utility.Constants.empty_last_name;

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
            throw new IllegalArgumentException(empty_first_name);
        }
        if(StringUtils.isBlank(lastName)){
            throw new IllegalArgumentException(empty_last_name);
        }
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }
}
