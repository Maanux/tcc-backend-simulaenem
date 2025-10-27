package SimulaEnem.service;

import SimulaEnem.domain.prova.ProvaQuestao;
import SimulaEnem.domain.prova.StatusProva;
import SimulaEnem.dto.Prova.*;
import SimulaEnem.repository.ProvaQuestaoRepository;
import SimulaEnem.repository.ProvaRepository;
import SimulaEnem.repository.QuestoesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
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

    @Transactional
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
                    nova.setTempoGasto(Duration.ZERO);
                    return nova;
                });


        Duration tempoAnterior = provaQuestao.getTempoGasto() != null
                ? provaQuestao.getTempoGasto()
                : Duration.ZERO;

        Duration novoTempo = Duration.ofSeconds(respostaRequest.tempoGasto());
        Duration tempoTotal = tempoAnterior.plus(novoTempo);

        provaQuestao.setAlternativaRespondida(respostaRequest.alternativaRespondidada().charAt(0));
        provaQuestao.setCorreta(correta);
        provaQuestao.setTempoGasto(tempoTotal);

        provaQuestaoRepository.save(provaQuestao);


        if (provaQuestao.getOrdem() != null && provaQuestao.getOrdem() > prova.getUltimaQuestaoOrdem()) {
            prova.setUltimaQuestaoOrdem(provaQuestao.getOrdem());
            prova.setUltimaAtividade(LocalDateTime.now());
            provaRepository.save(prova);
        }

        return new RespostaDTO(
                questao.getExternalId(),
                provaQuestao.getOrdem(),
                respostaRequest.alternativaRespondidada(),
                correta,
                tempoTotal.getSeconds()
        );
    }

    @Transactional
    public CheckpointProvaDTO pausarProva(UUID provaUuid, PausarProvaDTO dados) {
        var prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        prova.setStatus(StatusProva.PAUSADA);
        prova.setUltimaAtividade(LocalDateTime.now());

        // Atualiza última questão respondida
        if (dados.ultimaQuestaoRespondida() != null) {
            prova.setUltimaQuestaoOrdem(dados.ultimaQuestaoRespondida());
        }

        // Atualiza tempo total gasto
        if (dados.tempoGasto() != null) {
            Duration tempoAtual = prova.getTempoTotal() != null
                    ? prova.getTempoTotal()
                    : Duration.ZERO;

            Duration novoTempo = Duration.ofSeconds(dados.tempoGasto());
            prova.setTempoTotal(tempoAtual.plus(novoTempo));
        }

        provaRepository.save(prova);

        long totalQuestoes = provaQuestaoRepository.countByProvaExternalId(provaUuid);

        return new CheckpointProvaDTO(
                prova.getExternalId(),
                prova.getTitulo(),
                "PAUSADA",
                prova.getUltimaQuestaoOrdem(),
                prova.getUltimaQuestaoOrdem(), // ← MUDOU: próxima = mesma questão
                (int) totalQuestoes,
                prova.getTempoTotal() != null ? prova.getTempoTotal().getSeconds() : 0L,
                String.format("Prova pausada na questão %d", prova.getUltimaQuestaoOrdem())
        );
    }

    public CheckpointProvaDTO retomarProva(UUID provaUuid) {
        var prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        if (prova.getStatus() == StatusProva.FINALIZADA) {
            throw new RuntimeException("Esta prova já foi finalizada");
        }

        prova.setStatus(StatusProva.EM_ANDAMENTO);
        prova.setUltimaAtividade(LocalDateTime.now());
        provaRepository.save(prova);

        long totalQuestoes = provaQuestaoRepository.countByProvaExternalId(provaUuid);

        Integer proximaQuestao = prova.getUltimaQuestaoOrdem() + 1;

        return new CheckpointProvaDTO(
                prova.getExternalId(),
                prova.getTitulo(),
                "EM_ANDAMENTO",
                prova.getUltimaQuestaoOrdem(),
                proximaQuestao,
                (int) totalQuestoes,
                prova.getTempoTotal() != null ? prova.getTempoTotal().getSeconds() : 0L,
                String.format("Retomando da questão %d", proximaQuestao)
        );
    }

    public CheckpointProvaDTO obterStatusProva(UUID provaUuid) {
        var prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        long totalQuestoes = provaQuestaoRepository.countByProvaExternalId(provaUuid);

        Integer ultima = (prova.getUltimaQuestaoOrdem() != null) ? prova.getUltimaQuestaoOrdem() : 0;
        Integer proximaQuestao = ultima + 1;

        if (proximaQuestao > totalQuestoes) {
            proximaQuestao = (int) totalQuestoes;
        }

        String mensagem;
        if (ultima == 0) {
            mensagem = "Prova não iniciada. Comece pela questão 1";
        } else if (prova.getStatus() == StatusProva.PAUSADA) {
            mensagem = String.format("Prova pausada na questão %d", ultima);
        } else {
            mensagem = String.format("Continue da questão %d", proximaQuestao);
        }

        return new CheckpointProvaDTO(
                prova.getExternalId(),
                prova.getTitulo(),
                prova.getStatus().name(),
                ultima,
                proximaQuestao,
                (int) totalQuestoes,
                prova.getTempoTotal() != null ? prova.getTempoTotal().getSeconds() : 0L,
                mensagem
        );
    }


    @Transactional
    public ResultadoProvaDTO finalizarProva(UUID provaUuid) {
        var prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        if (prova.getStatus() == StatusProva.FINALIZADA) {
            throw new RuntimeException("Esta prova já foi finalizada anteriormente");
        }

        var questoesRespondidas = provaQuestaoRepository.findAllByProvaExternalId(provaUuid);

        long totalQuestoes = questoesRespondidas.size();
        long acertos = questoesRespondidas.stream()
                .filter(q -> Boolean.TRUE.equals(q.getCorreta()))
                .count();

        long erros = totalQuestoes - acertos;

        Duration tempoTotalGasto = questoesRespondidas.stream()
                .map(ProvaQuestao::getTempoGasto)
                .filter(tempo -> tempo != null)
                .reduce(Duration.ZERO, Duration::plus);

        prova.setStatus(StatusProva.FINALIZADA);
        prova.setDataFim(LocalDateTime.now());
        prova.setTempoTotal(tempoTotalGasto);
        prova.setUltimaAtividade(LocalDateTime.now());

        provaRepository.save(prova);

        double porcentagemAcerto = totalQuestoes > 0
                ? (acertos * 100.0) / totalQuestoes
                : 0.0;

        return new ResultadoProvaDTO(
                prova.getExternalId(),
                prova.getTitulo(),
                (int) totalQuestoes,
                (int) acertos,
                (int) erros,
                porcentagemAcerto,
                tempoTotalGasto.getSeconds(),
                prova.getDataInicio(),
                prova.getDataFim(),
                "Prova finalizada com sucesso!"
        );
    }
}

