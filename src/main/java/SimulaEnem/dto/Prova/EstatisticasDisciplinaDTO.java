package SimulaEnem.dto.Prova;

public record EstatisticasDisciplinaDTO(
        String disciplina,
        Integer total,
        Integer acertos,
        Integer erros,
        Double aproveitamento
) {}
