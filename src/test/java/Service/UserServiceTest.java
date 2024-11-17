package Service;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Utility.Constants.empty_first_name;
import static Utility.Constants.empty_last_name;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */

public class UserServiceTest {
    //create member variables here for reusability
    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password ;
    String repeatPassword;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        firstName = "Nickson";
        lastName = "Brown";
        email = "nickson.brown@gmail.com";
        password = "nickson";
        repeatPassword = "nickson";
    }

    @Test
    @DisplayName("User object created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        //act
        User user = userService.createUser(firstName, lastName, email , password , repeatPassword);  // create the test method in the UserService so as to continue
        //assert
        assertNotNull(user,"The CreateUser() method should not have returned null");
        assertEquals(firstName, user.getFirstName(), "The createUser() method should return correct firstname.");
        assertEquals(lastName, user.getLastName(), "The creteUse() method should return correct lastname");
        assertEquals(email, user.getEmail(), "The creteUse() method should return correct email");
        assertNotNull(user.getId(), "User id should not be null");
    }

    @Test
    @DisplayName("Empty first name throws correct Exception")
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        //arrange
        firstName= "";
        //act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(firstName, lastName, email , password , repeatPassword),
                "Empty first name should throw Illegal Argument Exception");
        //to also check if the correct message is thrown we assing the above method to a var

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
}
