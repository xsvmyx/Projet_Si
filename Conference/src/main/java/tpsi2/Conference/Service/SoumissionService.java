package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.Conference;
import tpsi2.Conference.Entities.Soumission;
import tpsi2.Conference.Entities.UserConferenceRole;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Enumeration.EtatConference;
import tpsi2.Conference.Model.SoumissionModele;
import tpsi2.Conference.Repositories.SoumissionRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;

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

           //on verifie si la conference est ouverte
           if(conference.getEtat().equals(EtatConference.OUVERTE)) {
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
               UserConferenceRole userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(utilisateur, "AUTEUR"
                       , Optional.empty());

               //on link la conference et on save
               userConferenceRole.setConference(conference);
               userConferenceRoleRepository.save(userConferenceRole);


               return ResponseEntity.ok().body("all good");
           }
           return ResponseEntity.badRequest().body("conference fermée");
       }

       return ResponseEntity.notFound().build();

    }

    public Soumission findSoumissionById(Long id) {
       return soumissionRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> checkSoumission(Long conferenceId) {
       Optional<Soumission> soumission = soumissionRepository.findById(conferenceId);
               if(soumission.isPresent()) {
                   return ResponseEntity.ok().body("conference exist");
               }
               return ResponseEntity.notFound().build();
    }

}
