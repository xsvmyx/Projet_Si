package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Model.SoumissionModele;
import tpsi2.Conference.Service.SoumissionService;
import tpsi2.Conference.Service.UtilisateurService;

@RestController
@RequestMapping ("/api/conference")
@AllArgsConstructor
public class SoumissionController {

    private final SoumissionService soumissionService;
    private final UtilisateurService utilisateurService;

    @PostMapping ("{conference}/soumission")
    @PreAuthorize("@utilisateurService.hasThisRole('AUTEUR')")
    public ResponseEntity<?> addSoumission (
            @PathVariable("conference") Long conference
            , @RequestBody SoumissionModele soumissionModele
    ) {
        return ResponseEntity.ok(soumissionService.createSoumission(soumissionModele, conference));

    }
}