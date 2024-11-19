package Service;

import data.UserRepository;
import data.UserRepositoryImpl;
import exception.UserServiceException;
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
    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    //this way when creating a new instance of UserServiceImpl any object as long as it implements the UserRepository
    //and we will use mockito to create an mock object that implement this interface
    public UserServiceImpl(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

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
       // UserRepository userRepository = new UserRepositoryImpl(); //todo : use constructor injection instead of creating objets
        boolean isUserCreated;
        try{
            isUserCreated = userRepository.save(user);
        }catch (RuntimeException e){
            throw new UserServiceException(e.getMessage());
        }
        if(!isUserCreated){
            throw new UserServiceException("Could not create user");
        }

        //send email
        try{
            emailVerificationService.scheduleMailConfirmation(user);
        }catch (RuntimeException e){
            throw new UserServiceException(e.getMessage());
        }
        return user;
    }
}
