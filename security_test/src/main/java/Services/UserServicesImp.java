package Services;

import Repository.RoleRepository;
import Repository.UserRepository;
import dormain.Role;
import dormain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class UserServicesImp implements UserServices{
    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRole(String username, String roleName) {
        User user=userRepo.findByUsername(username);
        Role role=roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
