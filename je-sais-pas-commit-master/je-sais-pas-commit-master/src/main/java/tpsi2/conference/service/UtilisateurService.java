package tpsi2.conference.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tpsi2.conference.config.UserPrincipal;
import tpsi2.conference.entities.UserConferenceRole;
import tpsi2.conference.entities.Utilisateur;
import tpsi2.conference.repositories.UserConferenceRoleRepository;
import tpsi2.conference.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateurService {

    private final UserRepository userRepository;
    private final UserConferenceRoleRepository userConferenceRoleRepository;

    //Methode pour voir tout les utilisateur
    public List<Utilisateur> getAllUtilisateurs() {
        return (List<Utilisateur>) userRepository.findAll();
    }

    //Methode pour void un utilisateur by username
    public Utilisateur getUtilisateurByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //Methode pour delete un utilisateur by username
  /*  public ResponseEntity<?> deleteUtilisateurByUsername(String username) {
        Utilisateur utilisateur = userRepository.findByUsername(username);
        if(utilisateur != null) {
            return  ResponseEntity.ok().body(userRepository.delete(username));
        }
        return ResponseEntity.notFound().build();
    }*/


    //Methode pour ajouter un utilisateur
    public ResponseEntity<?> addUser(Utilisateur utilisateur) {
        return ResponseEntity.ok().body(userRepository.save(utilisateur));
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


    public boolean hasRoleInConference(Long conferenceId, String role) {

        UserPrincipal user =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userConferenceRoleRepository.existsByUserIdAndConferenceIdAndRole(user.getUserId(), conferenceId, role);
    }

    public boolean hasRole(){
        UserPrincipal user =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional <Utilisateur> utilisateur =  userRepository.findById(user.getUserId());
        return utilisateur.get().getRole().equals("ADMIN");
    }

    public boolean hasRole(String role){
        UserPrincipal user =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Utilisateur> utilisateur = userRepository.findById(user.getUserId());
         UserConferenceRole utilisateurRole =   userConferenceRoleRepository.findByUser(utilisateur);

        return  utilisateurRole.getRole().equals(role);
    }
}
