package tpsi2.conference.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Auteur {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO) /*automatiser la valeur de la PK*/
    private int id;
    private String nom;
    private String prenom;
    @Column(name="infos", nullable=false , length=256)
    private String infos;


    //cascade all : si l'auteur est supprimé, alors toutes ses Soumissions vont etre supprimés
    //mapped by : l'attribut 'auteur' dans Soumission contrôle cette relation.
    @OneToMany(cascade=CascadeType.ALL,mappedBy =("auteur"))
    private Collection<Soumission> soumissions;

    public Auteur(String n,String p,String infos){
        this.nom=n;
        this.prenom=p;
        this.infos=infos;

    }


}
