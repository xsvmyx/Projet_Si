package tpsi2.conference.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.model.ConferenceModele;
import tpsi2.conference.service.ConferenceService;
import tpsi2.conference.service.UtilisateurService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class conferenceController {

    private final ConferenceService conferenceService;


 @PostMapping ("/conference")
 @PreAuthorize("@utilisateurService.hasRole('EDITEUR')")
    public ResponseEntity<?>addConference(@RequestBody ConferenceModele conference) {
     System.out.println("ta mere");
        return ResponseEntity.ok(conferenceService.createConference(conference));
    }
}
