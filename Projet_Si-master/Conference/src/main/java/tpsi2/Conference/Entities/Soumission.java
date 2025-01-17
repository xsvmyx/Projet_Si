package tpsi2.Conference.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany
    private Set<Utilisateur> evaluateurs;

    @OneToMany
    @JsonBackReference
    private Set<Evaluation> evaluations;

    public Soumission(){
        this.auteurs = new HashSet<>();
        this.evaluateurs = new HashSet<>();
        this.evaluations = new HashSet<>();
    }



    }
