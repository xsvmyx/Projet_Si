package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.conference.entities.Soumission;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long> {
}
