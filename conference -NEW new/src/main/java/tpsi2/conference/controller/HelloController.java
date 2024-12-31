package tpsi2.conference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tpsi2.conference.config.JwtDecoder;
import tpsi2.conference.config.JwtToPrincipal;
import tpsi2.conference.config.UserPrincipal;
import tpsi2.conference.entities.User;
import tpsi2.conference.model.LoginResponse;
import tpsi2.conference.repositories.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello this is public to everyone";
    }

    @GetMapping("/secured")
    public String secured() {


        return "if you seen this ,you're logged in ";
    }


    private final UserRepository userRepository;

    //comme /username mais on mieux
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(principal.getUsername() + " " + principal.getAuthorities()); // Retourne les détails utilisateur


    }

/*
    @GetMapping("/username")
    public String getUsername(@RequestHeader("Authorization") String token) {
        // Vérifie que le token commence bien par "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }

        //Décoder le token et récupérer le principal
        UserPrincipal userPrincipal = jwtToPrincipal.convert(jwtDecoder.decode(token));

        //Retourne le nom d'utilisateur
        return userPrincipal.getUsername() + " " + userPrincipal.getAuthorities();
    }*/


    @GetMapping("/admin")
    public String get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if (principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains("ADMIN"))) {
            return "Bonjour ADMIN!";
        }

        return "Hé CASSE TOI";
    }

//ceci est une demo, en pratique elle doit etre dans son service
    @PostMapping("/admin/{username}")
    public ResponseEntity<?> affecterRole(@PathVariable("username") String username) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if (principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains("ADMIN"))) {

            User s = userRepository.findByUsername(username);
            if(s!=null){
                s.setRole("ROLE");

            }
            else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user n'existe pas");


            return ResponseEntity.ok(userRepository.save(s));
        }

        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CASSE TOI");
    }


}