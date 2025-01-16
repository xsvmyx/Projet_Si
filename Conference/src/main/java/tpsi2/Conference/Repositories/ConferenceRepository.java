package tpsi2.Conference.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.Conference.Entities.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {


}
