package Service;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author nnkipkorir
 * created 15/11/2024
 */

public class UserServiceTest {


    @Test
    @DisplayName("User object created")
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        //arrange
        UserService userService = new UserServiceImpl(); //you cannot create instance of an interface (maybe a class that implements that interface )
        //prepare user details then pass it to the createUser method ( update the method accordingly)
        String firstName = "Nickson";
        String lastName = "Brown";
        String email = "nickson.brown@gmail.com";
        String password = "nickson";
        String repeatPassword = "nickson";

        //act
        User user = userService.createUser(firstName, lastName, email , password , repeatPassword);  // create the test method in the UserService so as to continue

        //assert
        assertNotNull(user,"The CreateUser() method should not have returned null");
        assertEquals(firstName, user.getFirstName(), "The createUser() method should return correct firstname.");
        assertEquals(lastName, user.getLastName(), "The creteUse() method should return correct lastname");
        assertEquals(email, user.getEmail(), "The creteUse() method should return correct email");
    }

}
