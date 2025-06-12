package SimulaEnem.service;

import SimulaEnem.domain.alternativas.Alternativas;
import SimulaEnem.repository.AlternativasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlternativasService {

    private final AlternativasRepository alternativasRepository;

    public AlternativasService(AlternativasRepository alternativasRepository) {
        this.alternativasRepository = alternativasRepository;
    }

    public List<Alternativas> listarPorIdQuestao(Long idQuestao) {
        return alternativasRepository.findByQuestaoIdOrderByLetterAsc(idQuestao);
    }
}
