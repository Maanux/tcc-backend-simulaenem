package SimulaEnem.controller;

import SimulaEnem.domain.alternativas.Alternativas;
import SimulaEnem.service.AlternativasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alternativas")
public class AlternativasController {

    private final AlternativasService alternativasService;

    public AlternativasController(AlternativasService alternativasService) {
        this.alternativasService = alternativasService;
    }

    @GetMapping("/idQuestao/{id}")
    public List<Alternativas> listarPorIdQuestao(@PathVariable Long id) {
        return alternativasService.listarPorIdQuestao(id);
    }
}
