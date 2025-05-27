package SimulaEnem.controller;

import SimulaEnem.domain.alternativas.Alternativas;

import SimulaEnem.repository.AlternativasRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("alternativas")
public class AlternativasController {

    @Autowired
    private AlternativasRepository alternativasRepository;

    @GetMapping("/idQuestao/{id}")
    public List<Alternativas> listarPorIdQuestao(@PathVariable Long id) {
        return alternativasRepository.findByQuestaoIdOrderByLetterAsc(id);
    }

}
