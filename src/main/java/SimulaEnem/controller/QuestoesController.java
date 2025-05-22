package SimulaEnem.controller;

import SimulaEnem.domain.questoes.Questoes;
import SimulaEnem.repository.QuestoesRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questoes")
public class QuestoesController {

    @Autowired
    private QuestoesRepository questoesRepository;

    @GetMapping
    public List<Questoes> listarQuestoes() {
        return questoesRepository.findAll();
    }

    @GetMapping("/ano/{year}")
    public List<Questoes> listarPorAno(@PathVariable int year) {
        return questoesRepository.findByYear(year, Sort.by(Sort.Direction.ASC, "index"));
    }

    @GetMapping("/disciplinas/{discipline}")
    public List<Questoes> listarPorDisciplinas(@PathVariable String discipline) {
        return questoesRepository.findByDiscipline(discipline);
    }

    @GetMapping("/linguagens/{language}")
    public List<Questoes> listarPorLinguagens(@PathVariable String language) {
        return questoesRepository.findByLanguage(language);
    }

}
