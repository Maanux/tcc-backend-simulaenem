package SimulaEnem.dto.Prova;

import java.util.UUID;

public record RespostaDTO(
        UUID questaoUuid,
        Integer ordem, //arrumar dps
        String alternativaRespondida,
        boolean correta
) {
}
