package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Service.UtilisateurService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/utilisateur")
    public ResponseEntity<List<Utilisateur>> getUtilisateur() {
        return ResponseEntity.ok().body(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping ("/utilisateur/{username}")
    public ResponseEntity<Utilisateur> getUtilisateurByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(utilisateurService.getUtilisateurByUsername(username));
    }

    @Deprecated //ah ya merwane
    @PostMapping("/assign-role/{username}")
    @PreAuthorize("@utilisateurService.hasRoleAdmin( )")
    public ResponseEntity<?> assignRoleToUser(
            @PathVariable String username,
            @RequestParam String role) {

         return ResponseEntity.ok().body(utilisateurService.assingRole(username, role));
    }
}
