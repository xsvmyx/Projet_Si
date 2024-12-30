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
