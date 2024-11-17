package data;

import model.User;

/**
 * @author nnkipkorir
 * created 17/11/2024
 */

public interface UserRepository {
   boolean save(User user);
}
