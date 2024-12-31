package tpsi2.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tpsi2.conference.Entities.*;
import tpsi2.conference.Repositories.RoleRepository;
import tpsi2.conference.Repositories.UserRepository;
import tpsi2.conference.Services.UserService;

@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

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
		UserApp user = new UserApp(2, "Salim", "123");
		userRepository.save(user);

		UserApp user2 = new UserApp(3, "Bob", "123");
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
