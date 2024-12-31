package tpsi2.conference.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.Config.JwtIssuer;
import tpsi2.conference.Entities.User;
import tpsi2.conference.Model.LoginRequest;
import tpsi2.conference.Model.LoginResponse;
import tpsi2.conference.Model.RegisterRequest;
import tpsi2.conference.Repositories.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final UserRepository userRepository;
//verifier et recuperer un USER de la BD
//envoyer les données du user dans jwt.issue()

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUSer(@RequestBody RegisterRequest r){
        if(userRepository.findByUsername(r.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username existe déjà");
        }
       // user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user = new User(r.getUsername(),r.getPassword());

        return ResponseEntity.ok(userRepository.save(user));
    }



    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest request){
        User u = userRepository.findByUsername(request.getUsername());
        if(u!= null && u.getPassword().equals(request.getPassword())){


            var token = jwtIssuer.issue(u.getId(), u.getUsername(), List.of(u.getRole()));

            return ResponseEntity.ok("Connexion réussie pour l'utilisateur : " + LoginResponse.builder().accessToken(token).build().getAccessToken());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
    }//le builder joue le role du constructeur




}
