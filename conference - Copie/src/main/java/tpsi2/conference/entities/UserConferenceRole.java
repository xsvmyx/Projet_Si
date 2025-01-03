package tpsi2.conference.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor



public class UserConferenceRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference

    private Utilisateur user;

    @ManyToOne
    @JsonManagedReference


    private Conference conference;

    private String role;

    public UserConferenceRole(Utilisateur user, String role) {
        this.user = user;
        this.role = role;
    }
    public UserConferenceRole(Utilisateur user, Conference conference, String role) {
        this.user = user;
        this.conference = conference;
        this.role = role;
    }
}
