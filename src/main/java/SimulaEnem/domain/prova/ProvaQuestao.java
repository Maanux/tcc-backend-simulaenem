package SimulaEnem.domain.prova;

import SimulaEnem.domain.questoes.Questoes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Table(name = "provas_questoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvaQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prova_id", nullable = false)
    private Prova prova;

    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questoes questao;

    private Integer ordem;

    @Column(name = "tempo_gasto")
    private Duration tempoGasto;

    @Column(name = "alternativa_respondida")
    private Character alternativaRespondida;

    private Boolean correta;
}
