package tpsi2.conference.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.model.ConferenceModele;
import tpsi2.conference.service.ConferenceService;
import tpsi2.conference.service.UtilisateurService;

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


}
