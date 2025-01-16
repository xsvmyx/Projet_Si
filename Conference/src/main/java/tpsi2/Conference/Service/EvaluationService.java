package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.Evaluation;
import tpsi2.Conference.Entities.Soumission;
import tpsi2.Conference.Entities.UserConferenceRole;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Enumeration.EtatEvaluation;
import tpsi2.Conference.Model.EvaluationModele;
import tpsi2.Conference.Repositories.EvaluationRepository;
import tpsi2.Conference.Repositories.SoumissionRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;
import tpsi2.Conference.Repositories.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final SoumissionService soumissionService;
    private final UtilisateurService utilisateurService;
    private final UserRepository userRepository;
    private final SoumissionRepository soumissionRepository;



    public ResponseEntity<?> createEvaluation(Long idSoumission , EvaluationModele evaluationModele) {

        Optional<Utilisateur> utilisateur = utilisateurService.getUserFromContext();

        if(!soumissionService.isSoumissionEvaluatedByUser(idSoumission,utilisateur.get())){

            //tout est ok, création de l'évaluation
            Evaluation evaluation = new Evaluation();

            //setting des infos
            evaluation.setNote(evaluationModele.getNote());
            evaluation.setCommentaire(evaluationModele.getCommentaire());
            evaluation.setEtatEvaluation(EtatEvaluation.REVISION);

            Soumission soumission = soumissionService.findSoumissionById(idSoumission);

            evaluation.setEvaluateur(utilisateur.get());
            evaluation.setSoumission(soumission);
            evaluationRepository.save(evaluation);

            utilisateur.get().getEvaluations().add(evaluation);
            userRepository.save(utilisateur.get());
            soumission.getEvaluations().add(evaluation);
            soumissionRepository.save(soumission);

            return ResponseEntity.ok().body("all good");

        }
        return ResponseEntity.badRequest().body("soumission deja évaluée par cet évaluateur.");

    }




}
