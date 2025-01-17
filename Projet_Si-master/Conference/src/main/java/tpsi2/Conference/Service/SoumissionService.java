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
                soumission.setNom(soumissionModele.getNom());
                soumission.setConference(conference);

                //Ajout de l'auteur principal
                soumission.getAuteurs().add(utilisateur.get());

                //Ajout des auteurs
                for (String username : soumissionModele.getAuteursUsername()) {
                    auteur = utilisateurService.getUtilisateurByUsername(username);
                    if (auteur == null) {
                        return ResponseEntity.notFound().build();
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

            return ResponseEntity.badRequest().body("conference fermée");
        }

        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<?> deleteSoumission(String noms, Long conferenceId) {

        Conference conference = conferenceService.getConference(conferenceId);

        if (conference != null) {

            //on verifie si la conference est ouverte
            if (conference.getEtat().equals(EtatConference.OUVERTE)) {

                soumissionRepository.delete(soumissionRepository.findByNom(noms));



                return ResponseEntity.ok().body("soumission deleted");
            }

            return ResponseEntity.badRequest().body("conference fermée");
        }

        return ResponseEntity.notFound().build();

    }
    public ResponseEntity<?> modifySoumission(String noms,String newnoms, Long conferenceId) {

        Conference conference = conferenceService.getConference(conferenceId);

        if (conference != null) {

            //on verifie si la conference est ouverte
            if (conference.getEtat().equals(EtatConference.OUVERTE)) {

                Soumission soumission = soumissionRepository.findByNom(noms);

                if (soumission != null) {
                    soumission.setNom(newnoms);
                    soumissionRepository.save(soumission);



                return ResponseEntity.ok().body("soumission modified");
            }
                return ResponseEntity.notFound().build();
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
        if (soumission.isPresent()) {
            return ResponseEntity.ok().body("conference exist");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> appendAuteur(Long SoumissionId, String username) {
        //pas besoin de verfier la conference car l'auteur bis ne doit pas forcement etre dans la meme conference
        Utilisateur auteur = utilisateurService.getUtilisateurByUsername(username);
        if (auteur != null) {
            Optional<Soumission> soumission = soumissionRepository.findById(SoumissionId);
            if (soumission.isPresent()) {
                soumission.get().getAuteurs().add(auteur);
                return ResponseEntity.ok().body("Auteur ajouté");
            }
        }
        return ResponseEntity.notFound().build();
    }
}
