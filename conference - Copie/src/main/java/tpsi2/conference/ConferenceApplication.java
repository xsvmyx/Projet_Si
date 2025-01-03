package tpsi2.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import tpsi2.conference.entities.Utilisateur;
import tpsi2.conference.repositories.ConferenceRepository;
import tpsi2.conference.repositories.UserRepository;


@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConferenceRepository conferenceRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Utilisateur user = new Utilisateur(1L,"admin","admin", "ADMIN");
		ResponseEntity.ok(userRepository.save(user));





	}
}
