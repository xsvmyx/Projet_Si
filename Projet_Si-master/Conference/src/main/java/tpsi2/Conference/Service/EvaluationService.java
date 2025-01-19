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

    //modifier Evaluation
    public ResponseEntity<?> modifyEvaluation(Long conferenceId,Long evaluationID, String newComm, Note newNote) {

        Conference conference = conferenceRepository.findById(conferenceId).orElse(null);
        if (conference != null) {

                Optional<Utilisateur> utilisateur = utilisateurService.getUserFromContext();
                //on fait ca pour recuperer qui envoie la demande
                Evaluation evaluation = evaluationRepository.findById(evaluationID).orElse(null);

                if (evaluation != null) {
                    if (conference.getEtat().equals(EtatConference.OUVERTE)) {
                        if(evaluation.getEvaluateur().equals(utilisateur)){
                    evaluation.setCommentaire(newComm);
                    evaluation.setNote(newNote);
                    evaluationRepository.save(evaluation);
                    return ResponseEntity.ok().body("Evaluation modifiée !");
                        }
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body("Vous n'etes pas autorisé ");
                    }
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body("Conference fermée");
        }

        return ResponseEntity.notFound().build();

    }

    //modifier etat evaluation pour l'Editeur
    public ResponseEntity<?> modifyEtatEvaluation( Long conferenceId,Long evaluationID,EtatEvaluation etatEvaluation) {
        Conference conference = conferenceRepository.findById(conferenceId).orElse(null);

        if (conference != null) {
            Evaluation evaluation = evaluationRepository.findById(evaluationID).orElse(null);
            if (evaluation != null) {

                if (conference.getEtat().equals(EtatConference.OUVERTE)) {
                    evaluation.setEtatEvaluation(etatEvaluation);
                    evaluationRepository.save(evaluation);
                    return ResponseEntity.ok().body("Etat d'évaluation modifiée !");
                }

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body("Conference fermée");
        }

        return ResponseEntity.notFound().build();

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
