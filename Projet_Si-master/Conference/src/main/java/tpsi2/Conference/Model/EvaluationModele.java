package tpsi2.Conference.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tpsi2.Conference.Enumeration.Note;

@AllArgsConstructor
@Getter
@Setter

public class EvaluationModele {
    private Note note;
    private String commentaire;

}
