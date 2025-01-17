package tpsi2.Conference.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.Conference.Model.ConferenceModele;
import tpsi2.Conference.Service.ConferenceService;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class conferenceController {

    private final ConferenceService conferenceService;


 @PostMapping
 @PreAuthorize("@utilisateurService.hasThisRole('EDITEUR')")
    public ResponseEntity<?>addConference(@RequestBody ConferenceModele conference) {
        return ResponseEntity.ok(conferenceService.createConference(conference));
    }

 @PutMapping ("/{conferenceId}/fermer")
 @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceId,'EDITEUR')")
    public ResponseEntity<?> closeConference(@PathVariable Long conferenceId) {

     return ResponseEntity.ok(conferenceService.fermerConference(conferenceId));
 }

 @PutMapping ("/{conferenceId}/ouvrir")
 @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceId,'EDITEUR')")
 public ResponseEntity<?> openConference(@PathVariable Long conferenceId) {

        return ResponseEntity.ok(conferenceService.ouvrirConference(conferenceId));
    }


 @DeleteMapping("/{conferenceId}/supprimer")
 @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceId,'EDITEUR')")
 public ResponseEntity<?> deleteConference(@PathVariable Long conferenceId) {

        return ResponseEntity.ok(conferenceService.supprimerConference(conferenceId));
    }


    @PutMapping ("/{conferenceId}/modifier")
    @PreAuthorize("@utilisateurService.hasRoleInConference(#conferenceId,'EDITEUR')")
    public ResponseEntity<?> modifyConference(@PathVariable Long conferenceId, @RequestBody ConferenceModele conf) {

        return ResponseEntity.ok(conferenceService.modifierConference(conferenceId,conf.getNom()));
    }



}
