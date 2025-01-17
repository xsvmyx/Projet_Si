package tpsi2.Conference.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.Conference.Entities.Soumission;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {
    Soumission findByIdAndNom(long id,String nom);
    Soumission findByNom(String nom);
}
