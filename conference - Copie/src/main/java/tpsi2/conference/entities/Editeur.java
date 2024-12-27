package tpsi2.conference.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Editeur {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO) /*automatiser la valeur de la PK*/
    private int id;
    private String nom;


    @OneToMany(cascade=CascadeType.ALL,mappedBy =("editeur"))
    @JsonManagedReference //pour éviter la récursivité infinie (la mort)
    private List<Conference> listconferences;

    public void addConference(Conference conference) {
        this.conferences.add(conference);
    }

}
