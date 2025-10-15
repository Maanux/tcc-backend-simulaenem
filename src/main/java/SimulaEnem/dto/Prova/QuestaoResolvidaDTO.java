package SimulaEnem.dto.Prova;

import SimulaEnem.dto.questao.QuestaoCompletaDTO;

public record QuestaoResolvidaDTO(
        Integer ordem,
        QuestaoCompletaDTO questaoCompletaDTO,
        String alternativaRespondida,
        Long tempoGasto,
        boolean correta
) {
}
