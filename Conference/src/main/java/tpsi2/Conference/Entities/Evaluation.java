package tpsi2.Conference.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tpsi2.Conference.Enumeration.EtatEvaluation;
import tpsi2.Conference.Enumeration.Note;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Evaluation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Note note;
    private String commentaire;
    private EtatEvaluation etatEvaluation;

    @ManyToOne
    @JsonBackReference
    private Soumission soumission;

    @ManyToOne
    @JsonBackReference
    private Utilisateur evaluateur;

    //ajout de la conference de l'evaluation pour faciliter la recherche
    @ManyToOne
    @JsonManagedReference
    private Conference conference;



}

