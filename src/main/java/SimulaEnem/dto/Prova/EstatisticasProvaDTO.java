package SimulaEnem.dto.Prova;

import java.util.UUID;

public record EstatisticasProvaDTO(
        UUID provaId,
        String titulo,
        String status,
        Integer totalQuestoes,
        Integer questoesRespondidas,
        Integer acertos,
        Integer erros,
        Double aproveitamento,
        Long tempoTotalSegundos,
        ComparativoAreasDTO comparativoAreas
) {}
