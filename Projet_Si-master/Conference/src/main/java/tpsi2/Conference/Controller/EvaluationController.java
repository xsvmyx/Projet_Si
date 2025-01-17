package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Model.EvaluationModele;
import tpsi2.Conference.Service.EvaluationService;
import tpsi2.Conference.Service.UtilisateurService;

@RestController
@RequestMapping ("/api/conference")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final UtilisateurService utilisateurService;

    @PostMapping ("soumission/{soumissionId}")
    @PreAuthorize("@utilisateurService.canEvaluateSoumission(#soumissionId)")
    public ResponseEntity<?> addEvaluation (
            @RequestBody EvaluationModele evaluationModele
            , @PathVariable Long soumissionId

    ) {
        return ResponseEntity.ok(evaluationService.createEvaluation(soumissionId, evaluationModele));

    }
}