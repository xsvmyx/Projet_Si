package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.conference.entities.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {


}
