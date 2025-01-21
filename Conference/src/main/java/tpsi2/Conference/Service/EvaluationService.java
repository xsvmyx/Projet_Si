package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.*;
import tpsi2.Conference.Enumeration.EtatConference;
import tpsi2.Conference.Enumeration.EtatEvaluation;
import tpsi2.Conference.Enumeration.Note;
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

            Soumission soumission = soumissionService.getSoumission(idSoumission).get();

            if(soumission.getConference().getEtat()!=EtatConference.FERME) {

                //tout est ok, création de l'évaluation
                Evaluation evaluation = new Evaluation();

                //setting des infos
                evaluation.setNote(evaluationModele.getNote());
                evaluation.setCommentaire(evaluationModele.getCommentaire());
                evaluation.setEtatEvaluation(EtatEvaluation.REVISION);


                evaluation.setEvaluateur(utilisateur.get());
                evaluation.setSoumission(soumission);
                evaluation.setConference(soumission.getConference());
                evaluationRepository.save(evaluation);

                utilisateur.get().getEvaluations().add(evaluation);
                userRepository.save(utilisateur.get());
                soumission.getEvaluations().add(evaluation);
                soumissionRepository.save(soumission);

                return ResponseEntity.ok().body("all good");
            }
            return ResponseEntity.badRequest().body("la conference est fermée, plus aucune évaluation n'est accéptée!");

        }
        return ResponseEntity.badRequest().body("soumission deja évaluée par cet évaluateur.");

    }

    public ResponseEntity<?> findEvaluationByConferenceId(Long conferenceId) {
        return conferenceRepository.findById(conferenceId)
                .map(conference -> {
                    List<Evaluation> evaluations = evaluationRepository.findAllByConference(conference);
                    if (evaluations.isEmpty()) {
                        return ResponseEntity.noContent().build();
                    }
                    return ResponseEntity.ok(evaluations);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //modifier Evaluation
    public ResponseEntity<?> modifyEvaluation(Long evaluationID,EvaluationModele evaluationModele) {

        Evaluation evaluation = evaluationRepository.findById(evaluationID).get();

        if(evaluation.getEtatEvaluation()==EtatEvaluation.REVISION) {
            evaluation.setCommentaire(evaluationModele.getCommentaire());
            evaluation.setNote(evaluationModele.getNote());
            evaluationRepository.save(evaluation);
            return ResponseEntity.ok().body("all good");
        }

        return ResponseEntity.badRequest().body("Une décision a deja été prise.");

    }

    //modifier etat evaluation pour l'Editeur
    public ResponseEntity<?> modifyEtatEvaluation( Long evaluationID,EtatEvaluation etatEvaluation) {

        Evaluation evaluation = evaluationRepository.findById(evaluationID).get();

        evaluation.setEtatEvaluation(etatEvaluation);
        evaluationRepository.save(evaluation);

        return ResponseEntity.ok().body("all good");

    }


    //Supprimer evaluation
    public ResponseEntity<?> deleteEvaluation( Long conferenceId,Long evaluationID) {
        Conference conference = conferenceRepository.findById(conferenceId).orElse(null);

        if (conference != null) {
            Evaluation evaluation = evaluationRepository.findById(evaluationID).orElse(null);
            if (evaluation != null) {

                if (conference.getEtat().equals(EtatConference.OUVERTE)) {
                    evaluationRepository.delete(evaluation);
                    return ResponseEntity.ok().body("Supprimée");
                }

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body("Conference fermée");
        }

        return ResponseEntity.notFound().build();

    }



}
