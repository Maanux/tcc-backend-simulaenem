package SimulaEnem.dto.questao;

import java.util.List;
import java.util.UUID;

public record QuestaoCompletaDTO(
        UUID externalId,
        String title,
        String discipline,
        int year,
        String context,
        List<String> files,
        String alternativesIntroduction,
        List<AlternativaDTO> alternativas
) {
    public record AlternativaDTO(
            char letter,
            String text,
            String file,
            boolean correct
    ) {}
}
