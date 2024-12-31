package tpsi2.conference.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;


@Entity
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("Auteur")
public class Auteur extends Role{
    private String infos;

    public Auteur() {
        super();
        this.setRoleName("Auteur");
    }


}
