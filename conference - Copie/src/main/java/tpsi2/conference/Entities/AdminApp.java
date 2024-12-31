package tpsi2.conference.Entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data

@Entity
@DiscriminatorValue("Admin")
public class AdminApp extends Role {
    private String infos;

    public AdminApp(){
        super();
        this.setRoleName("Admin");
    }

}
