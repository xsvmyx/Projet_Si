package tpsi2.conference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.config.JwtDecoder;
import tpsi2.conference.config.JwtToPrincipal;
import tpsi2.conference.config.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello this is public to everyone";
    }

    @GetMapping("/secured")
    public String secured(){


        return "if you seen this ,you're logged in ";
    }


    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipal jwtToPrincipal;

    @GetMapping("/username")
    public String getUsername(@RequestHeader("Authorization") String token) {
        // Vérifie que le token commence bien par "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }

        // Décoder le token et récupérer le principal
        UserPrincipal userPrincipal = jwtToPrincipal.convert(jwtDecoder.decode(token));

        // Retourne le nom d'utilisateur
        return userPrincipal.getUsername();
    }
}
