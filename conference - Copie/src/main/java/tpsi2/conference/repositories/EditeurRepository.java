package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tpsi2.conference.entities.Auteur;
import tpsi2.conference.entities.Editeur;

import java.util.List;

public interface EditeurRepository extends JpaRepository<Editeur,Long> {
    Editeur findById(long id);


    // List<Editeur> findAll();
}
