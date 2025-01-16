package tpsi2.Conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import tpsi2.Conference.Entities.Utilisateur;
import tpsi2.Conference.Repositories.ConferenceRepository;
import tpsi2.Conference.Repositories.UserRepository;


@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Utilisateur user = new Utilisateur(1L,"admin",passwordEncoder.encode("admin"), "ADMIN");
		ResponseEntity.ok(userRepository.save(user));





	}
}
