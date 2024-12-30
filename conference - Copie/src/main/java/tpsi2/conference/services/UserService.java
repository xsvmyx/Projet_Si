package tpsi2.conference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpsi2.conference.entities.Role;
import tpsi2.conference.entities.UserApp;
import tpsi2.conference.repositories.RoleRepository;
import tpsi2.conference.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
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

    public void addRoleToUser(String username, Role role) {
        userRepository.findByUsername(username).getRoles().add(role);
    }
    public List<UserApp> getAll() {
        return userRepository.findAll();
    }
//optional pour eviter l'erreur
    public Optional<UserApp> getUserById(Long id) {
        return userRepository.findById(id);
    }


}
