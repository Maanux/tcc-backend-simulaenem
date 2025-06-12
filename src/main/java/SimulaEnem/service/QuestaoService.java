package SimulaEnem.service;

import SimulaEnem.domain.ValidacaoException;
import SimulaEnem.dto.questao.QuestaoCompletaDTO;
import SimulaEnem.repository.AlternativasRepository;
import SimulaEnem.repository.QuestoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuestaoService {

    private final QuestoesRepository questoesRepository;
    private final AlternativasRepository alternativasRepository;

    public QuestaoCompletaDTO buscarQuestaoCompletaPorId(Long id) {
        var questao = questoesRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException ("Questão não encontrada"));

        var alternativas = alternativasRepository.findByQuestaoIdOrderByLetterAsc(id).stream()
                .map(a -> new QuestaoCompletaDTO.AlternativaDTO(
                        a.getLetter(),
                        a.getText(),
                        a.getFile(),
                        a.isCorrect()
                ))
                .toList();

        return new QuestaoCompletaDTO(
                questao.getTitle(),
                questao.getDiscipline(),
                questao.getYear(),
                questao.getContext(),
                questao.getFiles(),
                questao.getAlternativesIntroduction(),
                alternativas
        );
    }
}
