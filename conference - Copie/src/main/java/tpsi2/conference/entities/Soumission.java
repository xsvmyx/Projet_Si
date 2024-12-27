package tpsi2.conference.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Soumission{
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int id;
    private String nom;
    private String description;


    @ManyToOne
    private Auteur auteur;

    public Soumission(String nom, String description, Auteur auteur ) {
        this.nom = nom;
        this.description = description;
        this.auteur = auteur;
    }


}
