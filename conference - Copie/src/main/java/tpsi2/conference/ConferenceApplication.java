package tpsi2.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import tpsi2.conference.entities.*;
import tpsi2.conference.repositories.RoleRepository;
import tpsi2.conference.repositories.UserRepository;
import tpsi2.conference.services.UserService;

@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Création des rôles
		createRoleIfNotExists("Admin");
		createRoleIfNotExists("Auteur");
		createRoleIfNotExists("Evaluateur");

		// Création des utilisateurs
		UserApp user = new UserApp( "Salim", passwordEncoder.encode("123"));
		userRepository.save(user);
		System.out.println("voila le mdp :"+user.getPassword());

		UserApp user2 = new UserApp("Bob", "123");
		userRepository.save(user2);

		// Attribution du rôle à un utilisateur
		userService.addRoleToUser("Salim", "Auteur");
		userService.addRoleToUser("Bob", "Auteur");
	}

	private void createRoleIfNotExists(String roleName) {
		if (roleRepository.findByRoleName(roleName) == null) {
			Role role = switch (roleName) {
				case "Admin" -> new AdminApp();
				case "Auteur" -> new Auteur();
				case "Evaluateur" -> new Evaluateur();
				default -> throw new IllegalArgumentException("Role inconnu : " + roleName);
			};
			roleRepository.save(role);
			System.out.println("Role créé : " + roleName);
		}
	}
}
