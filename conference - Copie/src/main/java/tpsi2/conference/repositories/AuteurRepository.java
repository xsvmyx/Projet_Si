package tpsi2.conference.repositories;

import org.springframework.data.repository.CrudRepository;
import tpsi2.conference.entities.Auteur;

import java.util.ArrayList;

public interface AuteurRepository extends CrudRepository<Auteur,Long> {
    Auteur findByNom(String nom);
    Auteur findByPrenom(String prenom);
    ArrayList<Auteur> findByInfos(String Infos);
    Auteur findByNomAndPrenom(String nom,String prenom);




}
