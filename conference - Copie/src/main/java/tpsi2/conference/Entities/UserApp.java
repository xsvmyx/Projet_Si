package tpsi2.conference.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
//@Data sert a avoir le toString pour faire des verifications
@Entity
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToMany
    private Collection<Role> roles ;

    public UserApp(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserApp(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

}
