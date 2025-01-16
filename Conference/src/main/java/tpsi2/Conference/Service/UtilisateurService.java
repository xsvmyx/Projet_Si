package tpsi2.Conference.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tpsi2.Conference.Config.UserPrincipal;
import tpsi2.Conference.Entities.Conference;
import tpsi2.Conference.Entities.Soumission;
import tpsi2.Conference.Entities.UserConferenceRole;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Repositories.ConferenceRepository;
import tpsi2.Conference.Repositories.SoumissionRepository;
import tpsi2.Conference.Repositories.UserConferenceRoleRepository;
import tpsi2.Conference.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UtilisateurService {

    private final UserRepository userRepository;
    private final UserConferenceRoleRepository userConferenceRoleRepository;
    private final ConferenceRepository conferenceRepository;
    private final SoumissionRepository soumissionRepository;

    //Methode pour voir tout les utilisateur
    public List<Utilisateur> getAllUtilisateurs() {
        return (List<Utilisateur>) userRepository.findAll();
    }

    //Methode pour void un utilisateur by username
    public Utilisateur getUtilisateurByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //Methode pour ajouter un utilisateur
    public ResponseEntity<?> addUser(Utilisateur utilisateur) {
        return ResponseEntity.ok().body(userRepository.save(utilisateur));
    }

    //Methode pour avoir l'utilisateur courant
    public Optional<Utilisateur> getUserFromContext(){
        UserPrincipal user =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getUserId());
    }

    //Methode pour donner un role a un utilisateur
    public ResponseEntity<?> assingRole(String username,String role){
        Utilisateur utilisateur = userRepository.findByUsername(username);

        if(utilisateur != null){
            UserConferenceRole userConferenceRole = new UserConferenceRole();
            userConferenceRole.setRole(role);
            userConferenceRole.setUser(utilisateur);
            return ResponseEntity.ok().body(userConferenceRoleRepository.save(userConferenceRole));
        }
        return ResponseEntity.notFound().build();
    }

    //pour donner le role d'evaluateur
    public ResponseEntity<?> assignEvaluateurToConference(String username, String role, Long conferenceId, Long soumissionId){
        Utilisateur utilisateur = userRepository.findByUsername(username);

        if(utilisateur != null){
            Optional<Soumission> soumission1 = soumissionRepository.findById(soumissionId);
            if(soumission1.isEmpty()){
                //si pas de soumission on retourne erreur
                return ResponseEntity.notFound().build();
            }
            Soumission soumission = soumission1.get();

            if(soumission.getAuteurs().contains(utilisateur)){
                //on verifie que l'evaluateur n'est pas auteur
               return ResponseEntity.badRequest().build();
            }
            //sinon on cree l'evaluateur
            UserConferenceRole userConferenceRole = new UserConferenceRole();
            userConferenceRole.setRole(role);
            userConferenceRole.setUser(utilisateur);
            userConferenceRole.setConference(conferenceRepository.findById(conferenceId).get());
            //ajouter l'evaluateur au set d'evaluateur de la soumission
            soumission.getEvaluateurs().add(utilisateur);
            soumissionRepository.save(soumission);
            return ResponseEntity.ok().body(userConferenceRoleRepository.save(userConferenceRole));
        }
        return ResponseEntity.notFound().build();
    }

    //voir si a role dans une conference
    public boolean hasRoleInConference(Long conferenceId, String role) {

        Optional <Utilisateur> utilisateur = getUserFromContext();

       Optional <Conference> conference = conferenceRepository.findById(conferenceId);

        if(conference.isPresent()){

          UserConferenceRole  userConferenceRole = userConferenceRoleRepository.findByUserAndRoleAndConference(utilisateur,role,conference);
          return userConferenceRole != null;

        }
        return false;

    }

    //Methode pour voir si l'utilisateur est un admin
    public boolean hasRoleAdmin(){

        Optional <Utilisateur> utilisateur =  getUserFromContext();

        return utilisateur.get().getRole().equals("ADMIN");
    }

   //Methode pour voir si l'utilisateur a un role donné
    public boolean hasThisRole(String role){

        Optional<Utilisateur> utilisateur = getUserFromContext();
         UserConferenceRole utilisateurRole =   userConferenceRoleRepository.findByUserAndRoleAndConference(utilisateur, role, Optional.empty());

        return  utilisateurRole!=null;
    }

    //methode pour voir si l'utilisateur appartient a la liste des evaluateur de la soumission
    public boolean canEvaluateSoumission(Long soumissionId){
        Optional<Utilisateur> utilisateur = getUserFromContext();

        Optional<Soumission> soumission = soumissionRepository.findById(soumissionId);

        if(soumission.isPresent()){
            if(soumission.get().getEvaluateurs().contains(utilisateur.get())){
                return true;
            }
            return false;
        }
        return false;

    }

    
    
}
