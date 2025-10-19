package SimulaEnem.service;

import SimulaEnem.domain.prova.Prova;
import SimulaEnem.domain.prova.ProvaQuestao;
import SimulaEnem.dto.Prova.ComparativoAreasDTO;
import SimulaEnem.dto.Prova.EstatisticasAreaDTO;
import SimulaEnem.dto.Prova.EstatisticasDisciplinaDTO;
import SimulaEnem.dto.Prova.EstatisticasProvaDTO;
import SimulaEnem.repository.ProvaQuestaoRepository;
import SimulaEnem.repository.ProvaRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstatisticasProvaService {

    private final ProvaRepository provaRepository;
    private final ProvaQuestaoRepository provaQuestaoRepository;

    private static final Set<String> HUMANAS = Set.of(
            "ciencias-humanas",
            "linguagens"
    );

    private static final Set<String> EXATAS = Set.of(
            "matematica",
            "ciencias-natureza"
    );

    public EstatisticasProvaService(ProvaRepository provaRepository,
                                    ProvaQuestaoRepository provaQuestaoRepository) {
        this.provaRepository = provaRepository;
        this.provaQuestaoRepository = provaQuestaoRepository;
    }

    public EstatisticasProvaDTO obterEstatisticasGerais(UUID provaUuid) {
        Prova prova = provaRepository.findByExternalId(provaUuid)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));

        List<ProvaQuestao> questoes = provaQuestaoRepository
                .findAllByProvaExternalId(provaUuid);

        int totalQuestoes = questoes.size();

        long questoesRespondidas = questoes.stream()
                .filter(pq -> pq.getAlternativaRespondida() != null)
                .count();

        long acertos = questoes.stream()
                .filter(pq -> pq.getCorreta() != null && pq.getCorreta())
                .count();

        long erros = questoesRespondidas - acertos;

        double aproveitamento = questoesRespondidas > 0
                ? (acertos * 100.0) / questoesRespondidas
                : 0.0;

        Duration tempoTotal = questoes.stream()
                .map(ProvaQuestao::getTempoGasto)
                .filter(Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus);

        ComparativoAreasDTO comparativo = calcularComparativoAreas(questoes);

        return new EstatisticasProvaDTO(
                prova.getExternalId(),
                prova.getTitulo(),
                prova.getStatus().name(),
                totalQuestoes,
                (int) questoesRespondidas,
                (int) acertos,
                (int) erros,
                Math.round(aproveitamento * 100.0) / 100.0, // 2 casas decimais
                tempoTotal.getSeconds(),
                comparativo
        );
    }

    public List<EstatisticasDisciplinaDTO> obterEstatisticasPorDisciplina(UUID provaUuid) {
        List<ProvaQuestao> questoes = provaQuestaoRepository
                .findAllByProvaExternalId(provaUuid);

        Map<String, List<ProvaQuestao>> porDisciplina = questoes.stream()
                .collect(Collectors.groupingBy(
                        pq -> pq.getQuestao().getDiscipline()
                ));

        List<EstatisticasDisciplinaDTO> estatisticas = new ArrayList<>();

        for (Map.Entry<String, List<ProvaQuestao>> entry : porDisciplina.entrySet()) {
            String disciplina = entry.getKey();
            List<ProvaQuestao> questoesDisciplina = entry.getValue();

            int total = questoesDisciplina.size();

            long acertos = questoesDisciplina.stream()
                    .filter(pq -> pq.getCorreta() != null && pq.getCorreta())
                    .count();

            long respondidas = questoesDisciplina.stream()
                    .filter(pq -> pq.getAlternativaRespondida() != null)
                    .count();

            long erros = respondidas - acertos;

            double aproveitamento = respondidas > 0
                    ? (acertos * 100.0) / respondidas
                    : 0.0;

            estatisticas.add(new EstatisticasDisciplinaDTO(
                    disciplina,
                    total,
                    (int) acertos,
                    (int) erros,
                    Math.round(aproveitamento * 100.0) / 100.0
            ));
        }

        estatisticas.sort(Comparator.comparing(EstatisticasDisciplinaDTO::disciplina));

        return estatisticas;
    }

    private ComparativoAreasDTO calcularComparativoAreas(List<ProvaQuestao> questoes) {
        List<ProvaQuestao> questoesHumanas = questoes.stream()
                .filter(pq -> HUMANAS.contains(pq.getQuestao().getDiscipline()))
                .toList();

        List<ProvaQuestao> questoesExatas = questoes.stream()
                .filter(pq -> EXATAS.contains(pq.getQuestao().getDiscipline()))
                .toList();

        EstatisticasAreaDTO humanas = calcularEstatisticasArea(questoesHumanas);
        EstatisticasAreaDTO exatas = calcularEstatisticasArea(questoesExatas);

        return new ComparativoAreasDTO(humanas, exatas);
    }

    private EstatisticasAreaDTO calcularEstatisticasArea(List<ProvaQuestao> questoes) {
        if (questoes.isEmpty()) {
            return new EstatisticasAreaDTO(0, 0, 0, 0.0);
        }

        int total = questoes.size();

        long acertos = questoes.stream()
                .filter(pq -> pq.getCorreta() != null && pq.getCorreta())
                .count();

        long respondidas = questoes.stream()
                .filter(pq -> pq.getAlternativaRespondida() != null)
                .count();

        long erros = respondidas - acertos;

        double aproveitamento = respondidas > 0
                ? (acertos * 100.0) / respondidas
                : 0.0;

        return new EstatisticasAreaDTO(
                total,
                (int) acertos,
                (int) erros,
                Math.round(aproveitamento * 100.0) / 100.0
        );
    }
}
