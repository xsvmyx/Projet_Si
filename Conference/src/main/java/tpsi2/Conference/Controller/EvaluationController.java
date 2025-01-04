package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Service.EvaluationService;
import tpsi2.Conference.Service.UtilisateurService;

@RestController
@RequestMapping ("/api/conference")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final UtilisateurService utilisateurService;

    @PostMapping ("{conferenceID}/{soumissionID}/{note}")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EVALUATEUR')")
    public ResponseEntity<?> addEvaluation (
            @PathVariable Long conferenceID
            , @PathVariable Long soumissionID,
            @PathVariable Long note
    ) {
        return ResponseEntity.ok(evaluationService.createEvaluation(soumissionID,utilisateurService.getUserFromContextBis(),note));

    }
}