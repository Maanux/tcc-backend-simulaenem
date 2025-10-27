package SimulaEnem.dto.Prova;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstatisticasProvaDTO(
        UUID provaId,
        String titulo,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String status,
        Integer totalQuestoes,
        Integer questoesRespondidas,
        Integer acertos,
        Integer erros,
        Double aproveitamento,
        Long tempoTotalSegundos,
        ComparativoAreasDTO comparativoAreas
) {}
