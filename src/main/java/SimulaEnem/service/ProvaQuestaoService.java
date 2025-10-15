package SimulaEnem.service;

import SimulaEnem.domain.prova.ProvaQuestao;
import SimulaEnem.dto.Prova.ResponderQuestaoDTO;
import SimulaEnem.dto.Prova.RespostaDTO;
import SimulaEnem.repository.ProvaQuestaoRepository;
import SimulaEnem.repository.ProvaRepository;
import SimulaEnem.repository.QuestoesRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class ProvaQuestaoService {

    private final ProvaQuestaoRepository provaQuestaoRepository;
    private final QuestoesRepository questoesRepository;
    private final ProvaRepository provaRepository;

    public ProvaQuestaoService(ProvaQuestaoRepository provaQuestaoRepository,
                               QuestoesRepository questaoRepository,
                               ProvaRepository provaRepository) {
        this.provaQuestaoRepository = provaQuestaoRepository;
        this.questoesRepository = questaoRepository;
        this.provaRepository = provaRepository;
    }

    public RespostaDTO responderQuestao(UUID provaUuid, UUID questaoUuid, ResponderQuestaoDTO respostaRequest) {
        var prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        var questao = questoesRepository.findByExternalId(questaoUuid)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada"));

        boolean correta = String.valueOf(questao.getCorrectAlternative())
                .equalsIgnoreCase(respostaRequest.alternativaRespondidada());

        var provaQuestao = provaQuestaoRepository.findByProvaExternalIdAndQuestaoExternalId(provaUuid, questaoUuid)
                .orElseGet(() -> {
                    ProvaQuestao nova = new ProvaQuestao();
                    nova.setProva(prova);
                    nova.setQuestao(questao);
                    return nova;
                });

        provaQuestao.setAlternativaRespondida(respostaRequest.alternativaRespondidada().charAt(0));
        provaQuestao.setCorreta(correta);
        provaQuestao.setTempoGasto(Duration.ofSeconds(respostaRequest.tempoGasto()));

        provaQuestaoRepository.save(provaQuestao);

        return new RespostaDTO(
                questao.getExternalId(),
                provaQuestao.getOrdem(),
                respostaRequest.alternativaRespondidada(),
                correta
        );

    }

}
