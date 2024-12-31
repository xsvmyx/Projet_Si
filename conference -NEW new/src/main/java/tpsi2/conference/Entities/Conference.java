package tpsi2.conference.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roleParConference= new HashSet<>();
}
