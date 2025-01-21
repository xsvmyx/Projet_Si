package tpsi2.Conference.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.Conference.Config.JwtIssuer;

import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Model.LoginRequest;
import tpsi2.Conference.Model.LoginResponse;
import tpsi2.Conference.Model.RegisterRequest;
import tpsi2.Conference.Repositories.UserRepository;
import tpsi2.Conference.Service.UtilisateurService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final UserRepository userRepository;
    private final UtilisateurService  utilisateurService;
    private final PasswordEncoder passwordEncoder;
//verifier et recuperer un USER de la BD
//envoyer les données du user dans jwt.issue()


    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUSer(@RequestBody RegisterRequest r){

        if(userRepository.findByUsername(r.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username existe déjà");
        }
        Utilisateur user = new Utilisateur(r.getUsername(),r.getPassword(),"USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok(utilisateurService.addUser(user));
    }



    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest request){
        Utilisateur u = userRepository.findByUsername(request.getUsername());
        if(u!= null && passwordEncoder.matches(request.getPassword(),u.getPassword()) ){


            var token = jwtIssuer.issue(u.getId(), u.getUsername(), List.of(u.getRole()));

            return ResponseEntity.ok("Connexion réussie pour l'utilisateur : " + LoginResponse.builder().accessToken(token).build().getAccessToken());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
    }//le builder joue le role du constructeur




}
