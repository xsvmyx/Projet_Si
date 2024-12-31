package tpsi2.conference.Entities;



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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String username;
    private String password;
    private String role ;

    public User(String L,String p){
        username = L;
        password = p;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();


    public User(long l, String admin, String admin1, String admin2) {
        this.username = admin;
        this.password = admin1;
        this.role = admin2;
    }
}
