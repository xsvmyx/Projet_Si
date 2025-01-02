package tpsi2.conference.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tpsi2.conference.config.UserPrincipal;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.UserConferenceRole;
import tpsi2.conference.entities.Utilisateur;
import tpsi2.conference.model.ConferenceModele;
import tpsi2.conference.repositories.ConferenceRepository;
import tpsi2.conference.repositories.UserConferenceRoleRepository;
import tpsi2.conference.repositories.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final UserConferenceRoleRepository userConferenceRoleRepository;
    private final UserRepository userRepository;
    private final UtilisateurService utilisateurService;


    //Methode pour creer une conference
    public ResponseEntity <?> createConference(ConferenceModele conf) {

        Conference conference = new Conference();
        conference.setName(conf.getNom());
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
}
