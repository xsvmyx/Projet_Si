package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Service.UtilisateurService;

@RestController
@RequestMapping ("/api/assign-role")
@AllArgsConstructor
public class AffectationRoleController {

    private final UtilisateurService utilisateurService;

    //affectation Du Role Editeur par admin
    @PostMapping ("editeur/{username}")
    @PreAuthorize("@utilisateurService.hasRoleAdmin( )")
    public ResponseEntity<?> addRoleEditeur (@PathVariable String username) {
        return ResponseEntity.ok(utilisateurService.assingRole(username,"EDITEUR"));
    }

    @PostMapping ("auteur/{username}")
    @PreAuthorize("@utilisateurService.hasRoleAdmin( )")
    public ResponseEntity<?> addRoleAuteur (@PathVariable String username) {
        return ResponseEntity.ok(utilisateurService.assingRole(username,"AUTEUR"));
    }


    @PostMapping ("/{conferenceID}/evaluateur/{username}/{idSoumission}")
    //verifie si l'editeur est bien l'editeur de la conference
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> addRoleEvaluateur (@PathVariable String username
            , @PathVariable Long idSoumission
            , @PathVariable Long conferenceID
    ) {
        return ResponseEntity.ok(utilisateurService.assignEvaluateurToConference(username,"EVALUATEUR",conferenceID,idSoumission));

    }

}