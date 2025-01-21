package tpsi2.Conference.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.Conference.Entities.Soumission;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {
    Soumission findByIdAndTitre(long id,String nom);
    Soumission findByTitre(String nom);
}
