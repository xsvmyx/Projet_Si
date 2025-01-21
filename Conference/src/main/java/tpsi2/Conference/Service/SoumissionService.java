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
import tpsi2.Conference.Repositories.ConferenceRepository;
import tpsi2.Conference.Repositories.SoumissionRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;
import tpsi2.Conference.Repositories.UserRepository;

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
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;


    public boolean isSoumissionEvaluatedByUser(Long soumissionId,Utilisateur utilisateur) {
        Optional<Soumission> soumission = soumissionRepository.findById(soumissionId);

            return soumission.get().getEvaluations().stream().
                    anyMatch(evaluation -> evaluation.getEvaluateur().equals(utilisateur));

    }

    public ResponseEntity<?> createSoumission(SoumissionModele soumissionModele, Long conferenceId) {

        Conference conference = conferenceService.getConference(conferenceId);

        if (conference != null) {

            //on verifie si la conference est ouverte
            if (conference.getEtat().equals(EtatConference.OUVERTE)) {

                Utilisateur auteur;
                //on recupere l'utilisateur du contexte
                Optional<Utilisateur> utilisateur = utilisateurService.getUserFromContext();

                //creation de la soumission
                Soumission soumission = new Soumission();
                soumission.setTitre(soumissionModele.getNom());
                soumission.setConference(conference);
                soumission.setRésumé(soumissionModele.getRésumé());

                //Ajout de l'auteur principal
                soumission.getAuteurs().add(utilisateur.get());

                //Ajout des auteurs
                for (String username : soumissionModele.getAuteursUsername()) {
                    auteur = utilisateurService.getUtilisateurByUsername(username);
                    if (auteur == null) {
                        return ResponseEntity.badRequest().body("l'utilisateur"+username+" n'existe pas.");
                    }
                    soumission.getAuteurs().add(auteur);
                }

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

            return ResponseEntity.badRequest().body("la conference est fermée!");
        }

        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<?> deleteSoumission(String noms) {

        Soumission soumission = soumissionRepository.findByTitre(noms);
      Optional<Utilisateur> user = utilisateurService.getUserFromContext();

        if(soumission.getConference().getEtat().equals(EtatConference.FERME)) {
            return ResponseEntity.badRequest().body("conference fermée.");
        }

        for(Utilisateur utilisateur : soumission.getAuteurs()) {
            utilisateur.getSoumissions().remove(soumission);
            userRepository.save(utilisateur);
        }
        soumission.getConference().getSoumissions().remove(soumission);
        conferenceRepository.save(soumission.getConference());

        Optional<Conference> conferenceOptional = Optional.ofNullable(soumission.getConference());
        UserConferenceRole userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(user,"AUTEUR",conferenceOptional);
         userConferenceRole.setConference(null);
         userConferenceRoleRepository.save(userConferenceRole);

         soumissionRepository.delete(soumission);
        return  ResponseEntity.ok().body("all good.");

    }



    public ResponseEntity<?> modifySoumission(String soumissionNom,SoumissionModele soumissionModele) {

        Soumission soumission = soumissionRepository.findByTitre(soumissionNom);

        if(soumission.getConference().getEtat().equals(EtatConference.FERME)) {
            return ResponseEntity.badRequest().body("conference fermée.");
        }

        soumission.setTitre(soumissionModele.getNom());
        soumission.setRésumé(soumissionModele.getRésumé());

        soumissionRepository.save(soumission);

        return  ResponseEntity.ok().body("all good.");

    }

    public Optional<Soumission> getSoumission(Long id) {
        return soumissionRepository.findById(id);
    }


}
