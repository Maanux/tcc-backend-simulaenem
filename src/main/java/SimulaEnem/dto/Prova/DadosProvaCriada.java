package SimulaEnem.dto.Prova;

import SimulaEnem.dto.questao.QuestaoCompletaDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DadosProvaCriada(
        UUID externalId,
        String titulo,
        String status,
        LocalDateTime dataInicio,
        List<QuestaoCompletaDTO> questoes
) {}
