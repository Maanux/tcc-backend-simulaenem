package SimulaEnem.dto.Prova;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResultadoProvaDTO(
        UUID provaId,
        String titulo,
        Integer totalQuestoes,
        Integer acertos,
        Integer erros,
        Double porcentagemAcerto,
        Long tempoTotalSegundos,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String mensagem
) {}
