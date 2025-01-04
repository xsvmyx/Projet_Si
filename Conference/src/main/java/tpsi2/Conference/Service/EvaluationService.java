package tpsi2.Conference.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.Evaluation;
import tpsi2.Conference.Entities.Soumission;
import tpsi2.Conference.Entities.UserConferenceRole;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Repositories.EvaluationRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;
import tpsi2.Conference.Repositories.UserRepository;

import java.util.Optional;

@Service
public class EvaluationService {
    private final UserConferenceRoleRepository userConferenceRoleRepository;
    private final EvaluationRepository evaluationRepository;
    private final SoumissionService soumissionService;
    private final UserRepository userRepository;

    public EvaluationService(UserConferenceRoleRepository userConferenceRoleRepository, EvaluationRepository evaluationRepository, SoumissionService soumissionService, UserRepository userRepository) {
        this.userConferenceRoleRepository = userConferenceRoleRepository;
        this.evaluationRepository = evaluationRepository;
        this.soumissionService = soumissionService;
        this.userRepository = userRepository;

    }

    public ResponseEntity<?> createEvaluation(Long soumissionID, Utilisateur user,Long note) {
        Soumission soumission = soumissionService.findSoumissionById(soumissionID);
        if(soumission == null) {
            return ResponseEntity.notFound().build();
        }
        UserConferenceRole userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(Optional.ofNullable(user),"EVALUATEUR", Optional.ofNullable(soumission.getConference()));
        Evaluation evaluation = new Evaluation();
        evaluation.setSoumission(soumission);
        evaluation.setNote(note);
        evaluation.setUserConferenceRole(userConferenceRole);
        //je sais pas si c'est mieux d'ajouter juste le user (pas userconferenceRole)
        evaluationRepository.save(evaluation);
        return ResponseEntity.ok(evaluation);


    }

}
