package SimulaEnem.controller;

import SimulaEnem.dto.QuestaoCompletaDTO;
import SimulaEnem.repository.AlternativasRepository;
import SimulaEnem.repository.QuestoesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("questaoCompleta")
public class QuestaoController {

    private final QuestoesRepository questoesRepository;
    private final AlternativasRepository alternativasRepository;

    public QuestaoController(QuestoesRepository questoesRepository, AlternativasRepository alternativasRepository) {
        this.questoesRepository = questoesRepository;
        this.alternativasRepository = alternativasRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestaoCompletaDTO> getQuestaoCompleta(@PathVariable Long id) {

        var questao = questoesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questão não encontrada"));

        var alternativas = alternativasRepository.findByQuestaoIdOrderByLetterAsc(id).stream()
                .map(a -> new QuestaoCompletaDTO.AlternativaDTO(a.getLetter(), a.getText(), a.getFile(), a.isCorrect()))
                .toList();

        var dto = new QuestaoCompletaDTO(
                questao.getId(),
                questao.getTitle(),
                questao.getDiscipline(),
                questao.getYear(),
                questao.getContext(),
                questao.getFiles(),
                questao.getAlternativesIntroduction(),
                alternativas
        );

        return ResponseEntity.ok(dto);
    }
}
