package tpsi2.Conference.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
    private Long note;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Soumission soumission;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private UserConferenceRole userConferenceRole;
}

