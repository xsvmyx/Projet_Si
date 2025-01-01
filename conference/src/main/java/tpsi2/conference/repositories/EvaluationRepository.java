package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tpsi2.conference.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

}
