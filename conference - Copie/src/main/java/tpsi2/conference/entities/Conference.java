package tpsi2.conference.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import tpsi2.conference.enumeration.EtatConference;

import java.util.Set;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Conference  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private EtatConference etat;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<UserConferenceRole> userConferenceRoles;

    @OneToMany
    @JsonBackReference
    private Set<Soumission> soumissions;
}
