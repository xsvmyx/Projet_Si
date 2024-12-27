package tpsi2.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tpsi2.conference.entities.Auteur;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.Editeur;
import tpsi2.conference.entities.Soumission;
import tpsi2.conference.repositories.AuteurRepository;
import tpsi2.conference.repositories.ConferenceRepository;
import tpsi2.conference.repositories.EditeurRepository;
import tpsi2.conference.repositories.SoumissionRepository;
import tpsi2.conference.services.EditeurService;


@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}
	@Autowired
	private EditeurRepository editeurRepository;
	/*
	@Autowired //sans faire new
	private AuteurRepository auteurRepository;
	@Autowired
	private SoumissionRepository soumissionRepository;

	@Autowired
	private ConferenceRepository conferenceRepository;
	@Autowired
	private EditeurService editeurService;
*/
	@Override
	public void run(String... args) throws Exception {


/*

		Auteur a1 = new Auteur("Mahi","Samy","usthb");
		Auteur a2 = new Auteur("Dalil","Fayçal","usthb");
		Auteur a3 = new Auteur("Naoui","Khaled","usthb");

		auteurRepository.save(a1);
		auteurRepository.save(a2);
		auteurRepository.save(a3);



		Soumission s1 = new Soumission("Soumission1","Securité", a1 );
		Soumission s2 = new Soumission("Soumission1","IA", a1 );
		Soumission s3 = new Soumission("Soumission1","Big Data", a2 );
		Soumission s4 = new Soumission("Soumission1","IL", a3 );

		soumissionRepository.save(s1);
		soumissionRepository.save(s2);
		soumissionRepository.save(s3);
		soumissionRepository.save(s4);




		Conference c1 = new Conference("IA",e1);
		Conference c2 = new Conference("RSD",e2);
		Conference c3 = new Conference("cyber security",e2);

		conferenceRepository.save(c1);
		conferenceRepository.save(c2);
		conferenceRepository.save(c3);


*/

		Editeur e1 = new Editeur();
		e1.setNom("Mahi");
		Editeur e2 = new Editeur();
		e2.setNom("Makhlouf");
		editeurRepository.save(e1);
		editeurRepository.save(e2);

	}
}
