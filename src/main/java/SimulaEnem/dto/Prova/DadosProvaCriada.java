package SimulaEnem.dto.Prova;

import SimulaEnem.dto.questao.QuestaoCompletaDTO;

import java.time.LocalDateTime;
import java.util.List;

public record DadosProvaCriada(
        String titulo,
        String status,
        LocalDateTime dataInicio,
        List<QuestaoCompletaDTO> questoes
) {}
