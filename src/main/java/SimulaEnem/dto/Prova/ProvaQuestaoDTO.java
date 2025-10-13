package SimulaEnem.dto.Prova;

import SimulaEnem.dto.questao.QuestaoCompletaDTO;

import java.time.Duration;
import java.util.UUID;

public record ProvaQuestaoDTO(
        UUID externalId,
        Integer ordem,
        Character alternativaRespondida,
        Duration tempoGasto,
        QuestaoCompletaDTO questao
) { }
