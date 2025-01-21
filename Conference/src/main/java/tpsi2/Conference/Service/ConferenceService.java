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
        conference.setThématique(conf.getThématique());
        conference.setDateDeb(conf.getDebut());
        conference.setDateFin(conf.getFin());

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
        if(conference != null ) {
            if (conference.getEtat() != EtatConference.FERME) {

                conference.setEtat(EtatConference.FERME);
                conferenceRepository.save(conference);
                return ResponseEntity.ok("all good");

            } else {
                return ResponseEntity.badRequest().body("Conference deja fermée!");
            }
        }
        else return ResponseEntity.notFound().build();
    }


    public ResponseEntity <?> ouvrirConference(Long id) {

        Conference conference = getConference(id);
        if(conference != null ) {
            if (conference.getEtat() != EtatConference.OUVERTE) {

                conference.setEtat(EtatConference.OUVERTE);
                conferenceRepository.save(conference);
                return ResponseEntity.ok("all good");

            } else {
                return ResponseEntity.badRequest().body("Conference deja ouverte!");
            }
        }
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity <?> mettreEnEvaluationConference(Long id) {

        Conference conference = getConference(id);
        if(conference != null ) {
            if (conference.getEtat() != EtatConference.EN_EVALUATION) {

                conference.setEtat(EtatConference.EN_EVALUATION);
                conferenceRepository.save(conference);
                return ResponseEntity.ok("all good");

            } else {
                return ResponseEntity.badRequest().body("Conference deja en évaluation!");
            }
        }
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity <?> supprimerConference(Long id) {

        Conference conference = getConference(id);
        if(conference != null) {
            conferenceRepository.delete(conference);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }



    public ResponseEntity <?> modifierConference(Long id,String s) {

        Conference conference = getConference(id);
        if(conference != null) {
            conference.setName(s);
            conferenceRepository.save(conference);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }






}
