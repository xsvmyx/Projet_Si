package tpsi2.conference.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("Evaluateur")
public class Evaluateur extends Role {
    String infos;

    public Evaluateur() {
        super();
        this.setRoleName("Evaluateur");
    }
}
