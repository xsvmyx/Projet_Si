package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tpsi2.conference.entities.Auteur;
import tpsi2.conference.entities.Conference;

public interface ConferenceRepository extends JpaRepository<Conference,Long> {

}
