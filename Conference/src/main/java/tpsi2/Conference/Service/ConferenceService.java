package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Entities.Conference;
import tpsi2.Conference.Entities.Soumission;
import tpsi2.Conference.Entities.UserConferenceRole;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Enumeration.EtatConference;
import tpsi2.Conference.Model.ConferenceModele;
import tpsi2.Conference.Repositories.ConferenceRepository;
import tpsi2.Conference.Repositories.SoumissionRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;
import tpsi2.Conference.Repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final UserConferenceRoleRepository userConferenceRoleRepository;
    private final UserRepository userRepository;
    private final UtilisateurService utilisateurService;
    private final SoumissionRepository soumissionRepository;


    //Methode pour creer une conference
    public ResponseEntity <?> createConference(ConferenceModele conf) {

        Conference conference = new Conference();
        conference.setName(conf.getNom());
        conference.setEtat(EtatConference.OUVERTE);
        conferenceRepository.save(conference);

        Optional<Utilisateur> utilisateur = utilisateurService.getUserFromContext();

        UserConferenceRole userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(utilisateur,"EDITEUR",Optional.empty());
        userConferenceRole.setConference(conference);
        userConferenceRoleRepository.save(userConferenceRole);

       return ResponseEntity.ok("all good");
    }


    public Conference getConference(Long id) {
        return conferenceRepository.findById(id).orElse(null);
    }

    public ResponseEntity <?> fermerConference(Long id) {

        Conference conference = getConference(id);

        if(conference != null) {
            conference.setEtat(EtatConference.FERME);
            conferenceRepository.save(conference);
            return ResponseEntity.ok("all good");
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity <?> soumissionInConference(Long idConference,Long idSoumission,String nomSoumission) {
        Conference conference = getConference(idConference);
        if(conference != null) {
            //pour simuler un json ("String" : "String")
            Map<String,String> response1 = new HashMap<>();
            response1.put("message", "Conference not found");
            //je ne sais pas si c'est deprecated
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }else{
            //conference.getSoumissions().stream().findFirst(soumissionRepository.findByIdAndNom(idSoumission,nomSoumission));
            Optional<Soumission> soumissionTrouvee = conference.getSoumissions().stream()
                    .filter(soumission -> soumission.getId().equals(idSoumission) && soumission.getNom().equals(nomSoumission))
                    .findFirst();
            if(soumissionTrouvee.isPresent()) {
                return ResponseEntity.ok(soumissionTrouvee.get());
            }else{
                //pour simuler un json ("String" : "String")
                Map<String,String> response2 = new HashMap<>();
                response2.put("message", "La soumission n'est pas dans cette conference");
                //je ne sais pas si c'est deprecated
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response2);
            }

        }
    }
}
