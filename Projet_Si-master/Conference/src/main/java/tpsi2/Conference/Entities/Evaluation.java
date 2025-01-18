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
    @JsonManagedReference
    private Soumission soumission;

    @ManyToOne
    @JsonManagedReference
    private Utilisateur evaluateur;

    //ajout de la conference de l'evaluation pour faciliter la recherche
    @ManyToOne
    private Conference conference;



}

