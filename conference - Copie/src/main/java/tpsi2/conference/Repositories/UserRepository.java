package tpsi2.conference.Repositories;

import org.springframework.data.repository.CrudRepository;
import tpsi2.conference.Entities.UserApp;

import java.util.List;

public interface UserRepository extends CrudRepository<UserApp,Long> {
    UserApp findByUsername(String username);
    UserApp findById(long id);
    List<UserApp> findAll();





}
