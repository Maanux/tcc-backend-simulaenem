package SimulaEnem.domain.alternativas;

import SimulaEnem.domain.questoes.Questoes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alternativas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alternativas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questoes questoes;
    private char letter;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String file;
    private boolean isCorrect;
}
