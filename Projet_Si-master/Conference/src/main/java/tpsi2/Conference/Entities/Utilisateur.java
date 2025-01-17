package tpsi2.Conference.Entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String username;
    private String password;
    private String role ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<UserConferenceRole> userConferenceRoles;

    @ManyToMany(mappedBy = "auteurs")
    private Set<Soumission> soumissions = new HashSet<>();

    @OneToMany
    @JsonManagedReference
    private Set<Evaluation> evaluations = new HashSet<>();

    public Utilisateur(String L,String p){
        username = L;
        password = p;
    }


    public Utilisateur(long l, String admin, String admin1, String admin2) {
        username = admin;
        password = admin1;
        role = admin2;

    }

    public Utilisateur(String username, String password, String user) {

        this.username = username;
        this.password = password;
        this.role = user;

    }
}
