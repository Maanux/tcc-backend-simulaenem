package SimulaEnem.domain.prova;

import SimulaEnem.domain.baseEntity.BaseEntity;
import SimulaEnem.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "provas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prova  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "tempo_total")
    private Duration tempoTotal;

    @Enumerated(EnumType.STRING)
    private StatusProva status;

    @Column(name = "ultima_atividade")
    private LocalDateTime ultimaAtividade;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "ultima_questao_ordem")
    private Integer ultimaQuestaoOrdem = 0;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL)
    private List<ProvaQuestao> questoes;

}
