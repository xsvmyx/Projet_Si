package tpsi2.conference.Repositories;

import org.springframework.data.repository.CrudRepository;
import tpsi2.conference.Entities.Conference;
import tpsi2.conference.Entities.User;
import tpsi2.conference.Entities.UserRoleConf;

import java.util.Optional;

public interface UserRoleConfRepository extends CrudRepository<UserRoleConf,Long> {
    Optional<UserRoleConf> findByUserAndConference(User user, Conference conference);
}
