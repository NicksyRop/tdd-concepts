package Service;

import data.UserRepository;
import exception.EmailNotificationException;
import exception.UserServiceException;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static Utility.Constants.empty_first_name;
import static Utility.Constants.empty_last_name;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */


//todo: configure mockito to allow us use mockito annotations
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    //create member variables here for reusability
    //todo: to make mockito inject mocks for us we will need to use the service and not the interface nd use @InjectMocks annotation
    @InjectMocks
    UserServiceImpl userService;


    //todo : tell mockito what to do when we invoke methods in this interface
    @Mock
    UserRepository userRepository;
    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password ;
    String repeatPassword;

    @BeforeEach
    void setUp() {
       // userService = new UserServiceImpl(userRepository); // inject the mocked repository
        firstName = "Nickson";
        lastName = "Brown";
        email = "nickson.brown@gmail.com";
        password = "nickson";
        repeatPassword = "nickson";
    }

    @Test
    @DisplayName("User object created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        //arrange
        when(userRepository.save(any(User.class))).thenReturn(true); //save method requires a parameter, we are not calling a real save method so what we pass does not really matter
                                                                // hence we stab the data using mockito arguments matcher Mockito.any(Class)
        //act
        User user = userService.createUser(firstName, lastName, email , password , repeatPassword);  // create the test method in the UserService so as to continue
        //assert
        assertNotNull(user,"The CreateUser() method should not have returned null");
        assertEquals(firstName, user.getFirstName(), "The createUser() method should return correct firstname.");
        assertEquals(lastName, user.getLastName(), "The creteUse() method should return correct lastname");
        assertEquals(email, user.getEmail(), "The creteUse() method should return correct email");
        assertNotNull(user.getId(), "User id should not be null");
        //todo- ensure we call the save method only once - if twice test should fail
        //todo - takes in mock object and the desired number of expected invocation times  , then the method we calling i.e save() which accepts
        //todo - user object, hence we use mockito args passer - Mockito.any(User.class)
        verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Empty first name throws correct Exception")
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        //arrange
        firstName= "";
        //act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(firstName, lastName, email , password , repeatPassword),
                "Empty first name should throw Illegal Argument Exception");
        //to also check if the correct message is thrown we passing the above method to a var

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Empty first name should throw Illegal Argument Exception");

        assertEquals(empty_first_name, illegalArgumentException.getMessage(), "Missing first name did not throw correct exception message.");

    }

    @Test
    @DisplayName("Empty last name throws correct exception")
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        lastName = "";
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> userService.createUser(
                firstName, lastName, email, password, repeatPassword
        ), "Empty last name should throw valid exception");

        assertEquals(empty_last_name, illegalArgumentException.getMessage(), "Missing last name should throw a valid message");
    }

    @Test
    @DisplayName("if the save() method causes runtime exception,a userServiceException is thrown")
    void testCreateUser_whenSaveMethodThrowsException_thenThrowUserServiceException() {
        //arrange //todo - mock object exception stubbing
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class); //configure the save method to  throw RuntimeException exception when called
        //act
        //assert
        assertThrows(UserServiceException.class, () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Should have thrown UserServiceException instead");
    }

    @Test
    @DisplayName("Email notification exception is handled")
    void testCreateUser_whenEmailNotificationExceptionThrown_thenThrowUserServiceException() {
        //in the service class the save method either returns true or false -  isUserCreated = userRepository.save(user);
        //todo : when , theThrow , when thenReturn , do not work with void methods instead use doThrow().when();

        //arrange
        when(userRepository.save(any(User.class))).thenReturn(true);

        //we have configured emailVerificationService mock object to throw EmailNotificationException when scheduleMailConfirmation is
        //called
        doThrow(EmailNotificationException.class)
                .when(emailVerificationService)
                .scheduleMailConfirmation(any(User.class));
        //act and assert
        assertThrows(UserServiceException.class, () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Should have thrown UserServiceException instead");
        //assert - scheduleMailConfirmation was called only one time
        verify(emailVerificationService, times(1))
                .scheduleMailConfirmation(any(User.class));
    }

    @Test
    @DisplayName("Schedule email confirmation is executed")
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation() {
        //arrange - stub the save method
        when(userRepository.save(any(User.class))).thenReturn(true);
        //when you want mockito to call a real method
        //todo : the stub object should not be an abstract class , mockito should call real implementation
        doCallRealMethod().when(emailVerificationService).scheduleMailConfirmation(any(User.class));
        //act - call method under test
        userService.createUser(firstName, lastName, email, password, repeatPassword);
        //assert
        verify(emailVerificationService, times(1)).scheduleMailConfirmation(any(User.class));
    }
}
