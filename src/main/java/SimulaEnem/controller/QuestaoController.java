package SimulaEnem.controller;

import SimulaEnem.dto.questao.QuestaoCompletaDTO;
import SimulaEnem.service.QuestaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("questaoCompleta")
public class QuestaoController {

    private final QuestaoService questaoService;

    public QuestaoController(QuestaoService questaoService) {
        this.questaoService = questaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestaoCompletaDTO> getQuestaoCompleta(@PathVariable Long id) {
        var dto = questaoService.buscarQuestaoCompletaPorId(id);
        return ResponseEntity.ok(dto);
    }
}
