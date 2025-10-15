package SimulaEnem.dto.Prova;

import java.util.UUID;

public record CheckpointProvaDTO(
        UUID provaId,
        String titulo,
        String status,
        Integer ultimaQuestaoRespondida,
        Integer proximaQuestao,
        Integer totalQuestoes,
        Long tempoTotalGasto,
        String mensagem
) {}
