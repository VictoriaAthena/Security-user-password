package Services;


import dormain.Role;
import dormain.User;

import java.util.List;


public interface UserServices {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRole(String username, String roleName);
    User getUser(String username);
    List<User> getAllUsers();
}
