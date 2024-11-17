package data;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nnkipkorir
 * created 17/11/2024
 */

public class UserRepositoryImpl implements UserRepository {
    //todo : use Hashmap as a temporary storage to store user in memory
    Map<String, User> users = new HashMap<>();
    @Override
    public boolean save(User user) {
        boolean returnValue = false;
        //check if hashmap have a key passed / if not add to hashmap
        if(!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            returnValue = true;
        }
        return returnValue;
    }
}
