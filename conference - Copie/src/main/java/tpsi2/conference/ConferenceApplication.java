package tpsi2.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tpsi2.conference.entities.AdminApp;
import tpsi2.conference.entities.UserApp;
import tpsi2.conference.repositories.UserRepository;
import tpsi2.conference.services.UserService;


@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
    @Autowired
    private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}
	public void run(String... args) throws Exception {
		UserApp user = new UserApp(2,"Salim","123");
		userRepository.save(user);
		UserApp user2 = new UserApp(3,"Bob","123");
		userRepository.save(user2);
		userService.addRoleToUser("Salim", AdminApp.class.newInstance());
	}
}
