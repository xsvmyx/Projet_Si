package tpsi2.conference.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpsi2.conference.Entities.Role;
import tpsi2.conference.Entities.UserApp;
import tpsi2.conference.Repositories.RoleRepository;
import tpsi2.conference.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    UserService (UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //ne pas oublier d'ajouter l'email
    public UserApp addUser(UserApp user) {
        return userRepository.save(user);
    }

    public void addRoleToUser(String username, String roleName) {
        UserApp user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Aucun utilisateur: " + username);
        }

        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new RuntimeException("Role introuvable: " + roleName);
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }
    public List<UserApp> getAll() {
        return userRepository.findAll();
    }
//optional pour eviter l'erreur
    public Optional<UserApp> getUserById(Long id) {
        return userRepository.findById(id);
    }


}
