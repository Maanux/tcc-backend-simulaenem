package SimulaEnem.dto.Prova;

import SimulaEnem.domain.prova.StatusProva;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProvasDTO(
        UUID uuid,
        String titulo,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        int totalQuestoes,
        StatusProva statusProva
) {
}
