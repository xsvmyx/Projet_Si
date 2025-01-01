package tpsi2.conference.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.config.JwtIssuer;
import tpsi2.conference.entities.User;
import tpsi2.conference.model.LoginRequest;
import tpsi2.conference.model.LoginResponse;
import tpsi2.conference.model.RegisterRequest;
import tpsi2.conference.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final UserRepository userRepository;
//verifier et recuperer un USER de la BD
//envoyer les données du user dans jwt.issue()

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUSer(@RequestBody RegisterRequest r) {
        if (userRepository.findByUsername(r.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur existe déjà");
        }

        String encodedPassword = passwordEncoder.encode(r.getPassword());
        User user = new User(r.getUsername(), encodedPassword, r.getRole());
        userRepository.save(user);

        return ResponseEntity.ok("Création de l'utilisateur réussie");
    }




    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest request) {
        User u = userRepository.findByUsername(request.getUsername());

        if (u != null && passwordEncoder.matches(request.getPassword(), u.getPassword())) {
            String token = jwtIssuer.issue(u.getId(), u.getUsername(), List.of(u.getRole()));

            LoginResponse response = LoginResponse.builder()
                    .accessToken(token)
                    .build();

            return ResponseEntity.ok(Map.of(
                    "message", "Connexion réussie",
                    "accessToken", response.getAccessToken()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");

    }






}
