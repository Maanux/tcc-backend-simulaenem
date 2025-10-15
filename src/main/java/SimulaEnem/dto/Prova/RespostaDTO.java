package SimulaEnem.dto.Prova;

import java.util.UUID;

public record RespostaDTO(
        UUID questaoUuid,
        Integer ordem,
        String alternativaRespondida,
        boolean correta,
        Long tempoTotalGasto
) {
}
