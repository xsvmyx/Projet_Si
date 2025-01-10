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


    //V1
    /*
    //affectation du Role Evaluateur par editeur et envoie de la soumission
    @PostMapping ("/evaluateur/{username}/{idSoumission}")
    @PreAuthorize("@utilisateurService.hasThisRole('EDITEUR')")
    public ResponseEntity<?> addRoleEvaluateur (@PathVariable String username
                                                , @PathVariable Long idSoumission
    ) {
        //on recuperere l'editeur qui a fait la modification
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //on recupere sa conference (s'il en a une)
        Conference conferenceCible = utilisateurService.inWhichConference(authentication.getName());
        if (conferenceCible == null) {
            //pour simuler un json ("String" : "String")
           Map<String,String> response = new HashMap<>();
           response.put("message", "Conference not found");
           //je ne sais pas si c'est deprecated
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Soumission soumission = soumissionService.
        // verifier si soumission existe
        //verifier si editeur n'est pas auteur

        return ResponseEntity.ok(utilisateurService.assingRoleConference(username,"EVALUATEUR",));

    }*/

    @PostMapping ("/{conferenceID}/evaluateur/{username}/{idSoumission}")
    //verifie si l'editeur est bien l'editeur de la conference
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceID,'EDITEUR')")
    public ResponseEntity<?> addRoleEvaluateur (@PathVariable String username
            , @PathVariable Long idSoumission
            , @PathVariable Long conferenceID
    ) {
        return ResponseEntity.ok(utilisateurService.assignEditeurToConference(username,"EVALUATEUR",conferenceID,idSoumission));

    }

}