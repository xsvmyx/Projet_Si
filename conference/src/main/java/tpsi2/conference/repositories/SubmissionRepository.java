package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tpsi2.conference.entities.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
