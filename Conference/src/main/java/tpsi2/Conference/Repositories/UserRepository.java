package tpsi2.Conference.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.Conference.Entities.Utilisateur;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    //Object delete(String username);
}
