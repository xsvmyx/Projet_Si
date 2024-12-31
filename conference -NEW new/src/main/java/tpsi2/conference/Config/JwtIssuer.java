package tpsi2.conference.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issue(Long userId, String username, List<String> Roles){

        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("roles", Roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
        //le secret+algo sert de s'assurer que le token n'a pas était modifié (partie signature)
    }
}
