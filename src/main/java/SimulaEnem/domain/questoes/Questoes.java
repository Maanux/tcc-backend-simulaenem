package SimulaEnem.domain.questoes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "questoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Questoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int index;
    private String discipline;
    private String language;
    private int year;
    private String context;

    @ElementCollection
    private List<String> files;
    private char correctAlternative;
    private String alternativesIntroduction;
}
