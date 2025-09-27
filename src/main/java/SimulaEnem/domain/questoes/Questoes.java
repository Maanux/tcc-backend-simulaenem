package SimulaEnem.domain.questoes;

import SimulaEnem.domain.alternativas.Alternativas;
import SimulaEnem.domain.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "questoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Questoes  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int index;
    private String discipline;
    private String language;
    private int year;
    @Column(columnDefinition = "TEXT")
    private String context;

    @Column(columnDefinition = "text[]")
    private String[] files;

    private char correctAlternative;
    @Column(columnDefinition = "TEXT")
    private String alternativesIntroduction;
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alternativas> alternativas;

}
