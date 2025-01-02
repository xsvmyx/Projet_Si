package tpsi2.conference.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpsi2.conference.service.SoumissionService;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor

@Getter
@Setter

public class Soumission {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToMany
    private Set<Utilisateur> auteurs;

    @ManyToOne
    @JsonManagedReference
    private Conference conference;

    @OneToMany
    private Set<Utilisateur> evaluateurs;

    public Soumission(){
        this.auteurs = new HashSet<>();
        this.evaluateurs = new HashSet<>();
    }



    }
