package tpsi2.conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpsi2.conference.Entities.Conference;
import tpsi2.conference.Entities.Role;
import tpsi2.conference.Entities.User;
import tpsi2.conference.Entities.UserRoleConf;
import tpsi2.conference.Repositories.UserRoleConfRepository;

import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private UserRoleConfRepository userRoleConfRepository;

    public UserRoleConfRepository getUserRoleConfRepository() {
        return userRoleConfRepository;
    }
    public void setUserRoleConfRepository(UserRoleConfRepository userRoleConfRepository) {
        this.userRoleConfRepository = userRoleConfRepository;
    }

    public Set<Role> getRolesForConference(User user, Conference conference) {
        return userRoleConfRepository.findByUserAndConference(user, conference)
                .map(UserRoleConf::getRoles)
                .orElseThrow(() -> new RuntimeException("Rôles non trouvés pour cet utilisateur et cette conférence"));

    }
}
