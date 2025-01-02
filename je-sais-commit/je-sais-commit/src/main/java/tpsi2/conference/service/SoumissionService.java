package tpsi2.conference.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.Soumission;
import tpsi2.conference.entities.UserConferenceRole;
import tpsi2.conference.entities.Utilisateur;
import tpsi2.conference.model.SoumissionModele;
import tpsi2.conference.repositories.ConferenceRepository;
import tpsi2.conference.repositories.SoumissionRepository;
import tpsi2.conference.repositories.UserConferenceRoleRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Setter
@Getter

public class SoumissionService {

    private final SoumissionRepository soumissionRepository;
    private final UserConferenceRoleRepository userConferenceRoleRepository;

    private final ConferenceService conferenceService;
    private final UtilisateurService utilisateurService;




   public ResponseEntity<?> createSoumission(SoumissionModele soumissionModele,Long conferenceId) {

       Conference conference = conferenceService.getConference(conferenceId);

       if(conference != null) {

           //on recupere l'utilisateur du contexte
           Optional<Utilisateur> utilisateur = utilisateurService.getUserFromContext();

           //creation de la soumission
           Soumission soumission = new Soumission();
           soumission.setNom(soumissionModele.getNom());
           soumission.setConference(conference);
           soumission.getAuteurs().add(utilisateur.get());
           soumissionRepository.save(soumission);

           //on rajoute la soumission dans le set de soumission de la conférence
           conference.getSoumissions().add(soumission);

           //on recupere le tuple qui contient le user courant ayant comme role 'AUTEUR' et link dans aucune conference
           UserConferenceRole userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(utilisateur,"AUTEUR"
                   ,Optional.empty());

           //on link la conference et on save
           userConferenceRole.setConference(conference);
           userConferenceRoleRepository.save(userConferenceRole);


           return ResponseEntity.ok().body("all good");
       }

       return ResponseEntity.notFound().build();

    }
}
