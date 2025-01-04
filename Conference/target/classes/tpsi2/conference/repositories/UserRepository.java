package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.conference.entities.Utilisateur;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    //Object delete(String username);
}
