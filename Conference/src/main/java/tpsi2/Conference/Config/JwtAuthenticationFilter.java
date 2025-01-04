package tpsi2.Conference.Config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtToPrincipal jwtToPrincipal;
    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
       .map(jwtDecoder::decode)
                .map(jwtToPrincipal::convert)
                .map(UserPrincipalAuthenticationToken:: new )
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));




        filterChain.doFilter(request,response);
    }

    //on recupere le token de la requete
    //elle sera de la forme :
    //Authorization: Bearer eyzge658e43.ge478ge465.....
    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        //verifier si le String n'est pas vide
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            //on skip les 7 premiers caractere
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
