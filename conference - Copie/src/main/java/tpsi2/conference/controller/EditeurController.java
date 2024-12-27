package tpsi2.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.Editeur;
import tpsi2.conference.repositories.EditeurRepository;
import tpsi2.conference.services.EditeurService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Editeur")
public class EditeurController {



    private final EditeurService editeurService;

    @Autowired
    public EditeurController(EditeurService editeurService) {
        this.editeurService = editeurService;
    }

    @GetMapping
    public List<Editeur> getEditeurs(){
        return editeurService.getEditeurs();
    }

    @GetMapping(path={"{id}"})
    public Optional<Editeur> getEditeur(@PathVariable("id") Long id){

        return editeurService.getEditeur(id);
    }



    @PostMapping
    public void registerEditeur(@RequestBody Editeur e){ //@RequestBody : mapper le json en objet java automatiquemt
        editeurService.ajouterEditeur(e);
    }

    @DeleteMapping(path = "{id}")
    public void supprimerEditeurById(@PathVariable("id") Long id){
        editeurService.supprimerEditeur(id);
    }

    @PostMapping(path= "{id}/conferences")
    public void ajouterConference(@PathVariable("id") Long id,@RequestBody Conference c){

        editeurService.ajouterConference(id,c);

    }

     @GetMapping(path= "{id}/conferences")
    public List<Conference> getEditeurConferences(@PathVariable("id") Long id){
        return editeurService.getEditeurConferences(id);
    }


    @DeleteMapping(path= "{id}/{idc}")
    public void supprimerConference(@PathVariable Long id,@PathVariable Long idc){
        editeurService.supprimerConference(id,idc);
    }
}
