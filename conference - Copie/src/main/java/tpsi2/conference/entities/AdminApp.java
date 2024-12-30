package tpsi2.conference.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

@Entity
@DiscriminatorValue("Admin")
public class AdminApp extends Role {
    private String infos;


}
