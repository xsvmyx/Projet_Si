package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.*;
import tpsi2.Conference.Enumeration.EtatEvaluation;
import tpsi2.Conference.Model.EvaluationModele;
import tpsi2.Conference.Repositories.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final SoumissionService soumissionService;
    private final UtilisateurService utilisateurService;
    private final UserRepository userRepository;
    private final SoumissionRepository soumissionRepository;
    private final ConferenceRepository conferenceRepository;


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

    public List<Evaluation> findEvaluationByConferenceId(Long conferenceId) {
        Optional<Conference> conference = conferenceRepository.findById(conferenceId);
        return (List<Evaluation>) evaluationRepository.findAllByConference(conference.orElse(null));
    }




}
