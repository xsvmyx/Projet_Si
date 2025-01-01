package tpsi2.conference.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Evaluation {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private String comments;
    private String state; // "accepted", "rejected", "revision"

    @ManyToOne
    private Submission submission;

    @ManyToOne
    private User reviewer;


}

