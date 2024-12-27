package tpsi2.conference.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Conference {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int id;
    private String titre;


    @ManyToOne
    @JsonBackReference
    Editeur editeur;


   public Conference(String t,Editeur e){titre=t;editeur=e;}
}
