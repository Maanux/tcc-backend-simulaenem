package SimulaEnem.domain.alternativas;

import SimulaEnem.domain.questoes.Questoes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alternativas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alternativas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    @JsonIgnore
    private Questoes questao;
    private char letter;

    @Column(columnDefinition = "TEXT")
    private String text;
    private String file;
    private boolean isCorrect;
}
