package tpsi2.conference.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tpsi2.conference.entities.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
