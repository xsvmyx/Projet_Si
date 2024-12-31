package tpsi2.conference;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/").permitAll() // Permet l'accès public à "/"
                                .anyRequest().authenticated()   // Toutes les autres requêtes doivent être authentifiées
                )
                .formLogin(l -> l.defaultSuccessUrl("/private"))
                .logout(l -> l.logoutSuccessUrl("/"))
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        //cree un admin ici aussi
                        .username("admin")
                        .password("{noop}admin")
                        .roles("Admin")
                        .build()
        );
    }
}
