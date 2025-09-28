package SimulaEnem.dto.Prova;

import java.util.UUID;

public record DadosParaCriarProvaPorDisciplina(
        UUID externalId,
        Integer cienciasHumanas,
        Integer cienciasNatureza,
        Integer linguagens,
        Integer matematica,
        Integer ingles,
        Integer espanhol
) {
}
