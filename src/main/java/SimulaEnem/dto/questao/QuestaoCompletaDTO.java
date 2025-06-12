package SimulaEnem.dto.questao;

import java.util.List;

public record QuestaoCompletaDTO(
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
