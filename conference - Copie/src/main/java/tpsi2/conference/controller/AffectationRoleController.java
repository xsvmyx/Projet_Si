package tpsi2.conference.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.Soumission;
import tpsi2.conference.entities.Utilisateur;
import tpsi2.conference.model.SoumissionModele;
import tpsi2.conference.repositories.UserRepository;
import tpsi2.conference.service.SoumissionService;
import tpsi2.conference.service.UtilisateurService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/api/assign-role")
@AllArgsConstructor
public class AffectationRoleController {

    private final UtilisateurService utilisateurService;
    private final SoumissionService soumissionService;

    //affectation Du Role Editeur par admin
    @PostMapping ("editeur/{username}")
    @PreAuthorize("@utilisateurService.hasRoleAdmin( )")
    public ResponseEntity<?> addRoleEditeur (@PathVariable String username) {
        return ResponseEntity.ok(utilisateurService.assingRole(username,"EDITEUR"));
    }

    //affectation du Role Evaluateur par editeur et envoie de la soumission
    @PostMapping ("evaluateur/{username}/{idSoumission}")
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

    }
}