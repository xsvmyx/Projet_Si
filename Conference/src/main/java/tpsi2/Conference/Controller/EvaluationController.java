package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Enumeration.EtatEvaluation;
import tpsi2.Conference.Model.EvaluationModele;
import tpsi2.Conference.Model.SoumissionModele;
import tpsi2.Conference.Service.EvaluationService;
import tpsi2.Conference.Service.UtilisateurService;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final UtilisateurService utilisateurService;

    @PostMapping("evaluation/{soumissionId}")
    @PreAuthorize("@utilisateurService.canEvaluateSoumission(#soumissionId)")
    public ResponseEntity<?> addEvaluation (
            @RequestBody EvaluationModele evaluationModele
            , @PathVariable Long soumissionId

    ) {
        return ResponseEntity.ok(evaluationService.createEvaluation(soumissionId, evaluationModele));

    }
    //A TESTER POUR SALIM
//ajout d'evaluation consulter Merwane demain + Essayer de faire une meilleur implementation
    @GetMapping  ("{conferenceID}/evaluations")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> checkEvaluations (
            @PathVariable Long conferenceID

    ) {
        return ResponseEntity.ok(evaluationService.findEvaluationByConferenceId(conferenceID));

    }
 //Un evaluateur peut modifier ses evaluations
 //un editeur peut supprimer des evaluations
 //un editeur peut changer l'etat de l'evaluation

    @PutMapping("evaluation/{evaluationID}/modifier")
    @PreAuthorize("@utilisateurService.isEvaluateur(#evaluationID)")
    public ResponseEntity<?> modifiyEvaluation(
            @PathVariable("evaluationID") Long evaluationID,
            @RequestBody EvaluationModele evaluationModele
    ) {
        return ResponseEntity.ok(evaluationService.modifyEvaluation(evaluationID,evaluationModele));

    }

    @PutMapping("{conferenceID}/evaluation/{evaluationId}/modifierEtat/accepter")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> modifiyEtatEvaluationAccept(

            @PathVariable("evaluationId") Long evaluationID, @PathVariable Long conferenceID) {
        return ResponseEntity.ok(evaluationService.modifyEtatEvaluation(evaluationID,EtatEvaluation.ACCEPTEE));

    }
    @PutMapping("{conferenceID}/evaluation/{evaluationID}/modifierEtat/rejeter")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> modifiyEtatEvaluationReject(
            @PathVariable("evaluationID") Long evaluationID,
            @PathVariable Long conferenceID)
             {
        return ResponseEntity.ok(evaluationService.modifyEtatEvaluation(evaluationID,EtatEvaluation.REJETEE));

    }

    @PutMapping("{conferenceID}/{evaluationID}/delete")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> supprimerEvaluation(
            @PathVariable("conferenceID") Long conferenceID,
            @PathVariable("evaluationID") Long evaluationID
    ) {
        return ResponseEntity.ok(evaluationService.deleteEvaluation(conferenceID,evaluationID));

    }


}
