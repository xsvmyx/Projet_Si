package tpsi2.conference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.Editeur;
import tpsi2.conference.repositories.ConferenceRepository;
import tpsi2.conference.repositories.EditeurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EditeurService {


    private final EditeurRepository editeurRepository;
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public EditeurService(EditeurRepository editeurRepository, ConferenceRepository conferenceRepository) {
        this.editeurRepository = editeurRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public List<Editeur> getEditeurs(){
        return editeurRepository.findAll();
    }
    public Optional<Editeur> getEditeur(Long id){
        if(!editeurRepository.existsById(id)){
            throw new IllegalStateException("Editeur n'existe pas");
        }
        return editeurRepository.findById(id);
    }

    public void ajouterEditeur(Editeur e){

        System.out.println(e.getNom());

        editeurRepository.save(e);
    }


    public void supprimerEditeur(Long id){
        if(!editeurRepository.existsById(id)){
            throw new IllegalStateException("Editeur existe deja");

        }else
            editeurRepository.deleteById(id);
    }


    public void ajouterConference(Long id, Conference c){

        Editeur e = editeurRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Editeur n'existe pas"));

        e.addConference(c);
        c.setEditeur(e);


        conferenceRepository.save(c);
    }

    public List<Conference> getEditeurConferences(Long id){
        Editeur e = editeurRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Editeur n'existe pas"));
        return e.getListconferences();

    }
    
    public void supprimerConference(Long id, Long idc){

        Editeur e = editeurRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Editeur n'existe pas"));

        Conference c = conferenceRepository.findById(idc)
                .orElseThrow(() -> new IllegalStateException("Conference n'existe pas"));

        e.getListconferences().remove(c);

        editeurRepository.save(e);
        conferenceRepository.delete(c);

    }

}
