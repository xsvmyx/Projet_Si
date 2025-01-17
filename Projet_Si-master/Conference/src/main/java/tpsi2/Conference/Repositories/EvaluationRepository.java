package tpsi2.Conference.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpsi2.Conference.Entities.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
