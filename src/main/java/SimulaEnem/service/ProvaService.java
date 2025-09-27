package SimulaEnem.service;

import SimulaEnem.domain.ValidacaoException;
import SimulaEnem.domain.prova.Prova;
import SimulaEnem.domain.prova.ProvaQuestao;
import SimulaEnem.domain.questoes.Questoes;
import SimulaEnem.domain.usuario.Usuario;
import SimulaEnem.dto.Prova.DadosProvaCriada;
import SimulaEnem.dto.Prova.ProvaQuestaoDTO;
import SimulaEnem.dto.questao.QuestaoCompletaDTO;
import SimulaEnem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProvaService {

    @Autowired
    private final ProvaRepository provaRepository;
    @Autowired
    private final QuestoesRepository questoesRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final AlternativasRepository alternativasRepository;
    @Autowired
    private final ProvaQuestaoRepository provaQuestaoRepository;

    public DadosProvaCriada criarProvaAleatoria(String externalId, int quantidadeQuestoes) {

        UUID uuid;
        try {
            uuid = UUID.fromString(externalId);
        } catch (IllegalArgumentException e) {
            throw new ValidacaoException("UUID do usuário inválido.");
        }
        Usuario usuario = usuarioRepository.findByExternalId(uuid)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));

        Prova prova = new Prova();
        prova.setTitulo("Prova gerada automaticamente");
        prova.setStatus("em_andamento");
        prova.setDataInicio(LocalDateTime.now());
        prova.setUltimaAtividade(LocalDateTime.now());
        prova.setUsuario(usuario);

        List<Questoes> questoes = questoesRepository.buscarQuestoesAleatorias(quantidadeQuestoes);
        List<ProvaQuestao> provaQuestoes = new ArrayList<>();

        int ordem = 1;
        for (Questoes q : questoes) {
            ProvaQuestao pq = new ProvaQuestao();
            pq.setProva(prova);
            pq.setQuestao(q);
            pq.setOrdem(ordem++);
            pq.setTempoGasto(Duration.ZERO);
            provaQuestoes.add(pq);
        }

        prova.setQuestoes(provaQuestoes);
        provaRepository.save(prova);


        List<QuestaoCompletaDTO> questoesDTO = questoes.stream().map(q -> {
            var alternativas = alternativasRepository.findByQuestaoIdOrderByLetterAsc(q.getId()).stream()
                    .map(a -> new QuestaoCompletaDTO.AlternativaDTO(
                            a.getLetter(), a.getText(), a.getFile(), a.isCorrect()
                    ))
                    .toList();

            return new QuestaoCompletaDTO(
                    q.getExternalId(),
                    q.getTitle(),
                    q.getDiscipline(),
                    q.getYear(),
                    q.getContext(),
                    Arrays.asList(q.getFiles()),
                    q.getAlternativesIntroduction(),
                    alternativas
            );
        }).toList();

        return new DadosProvaCriada(
                prova.getExternalId(),
                prova.getTitulo(),
                prova.getStatus(),
                prova.getDataInicio(),
                questoesDTO
        );
    }


    public DadosProvaCriada mapearParaDTO(Prova prova) {
        List<QuestaoCompletaDTO> questoesDTO = prova.getQuestoes().stream().map(pq -> {
            var q = pq.getQuestao();
            var alternativas = alternativasRepository.findByQuestaoIdOrderByLetterAsc(q.getId()).stream()
                    .map(a -> new QuestaoCompletaDTO.AlternativaDTO(a.getLetter(), a.getText(), a.getFile(), a.isCorrect()))
                    .toList();

            return new QuestaoCompletaDTO(
                    q.getExternalId(),
                    q.getTitle(),
                    q.getDiscipline(),
                    q.getYear(),
                    q.getContext(),
                    Arrays.asList(q.getFiles()),
                    q.getAlternativesIntroduction(),
                    alternativas
            );
        }).toList();

        return new DadosProvaCriada(
                prova.getExternalId(),
                prova.getTitulo(),
                prova.getStatus(),
                prova.getDataInicio(),
                questoesDTO
        );
    }

    public ProvaQuestaoDTO buscarQuestaoCompletaPorProvaIdEOrdem(Long provaId, Integer ordem) {
        ProvaQuestao provaQuestao = provaQuestaoRepository.findByProvaIdAndOrdem(provaId, ordem)
                .orElseThrow(() -> new ValidacaoException("Questão não encontrada na prova."));

        Questoes questao = provaQuestao.getQuestao();

        List<QuestaoCompletaDTO.AlternativaDTO> alternativas = questao.getAlternativas()
                .stream()
                .map(a -> new QuestaoCompletaDTO.AlternativaDTO(
                        a.getLetter(),
                        a.getText(),
                        a.getFile(),
                        a.isCorrect()
                ))
                .toList();


        QuestaoCompletaDTO questaoCompletaDTO = new QuestaoCompletaDTO(
                questao.getExternalId(),
                questao.getTitle(),
                questao.getDiscipline(),
                questao.getYear(),
                questao.getContext(),
                Arrays.asList(questao.getFiles()),
                questao.getAlternativesIntroduction(),
                alternativas
        );


        return new ProvaQuestaoDTO(
                provaQuestao.getProva().getExternalId(),
                provaQuestao.getOrdem(),
                provaQuestao.getAlternativaRespondida(),
                provaQuestao.getTempoGasto(),
                questaoCompletaDTO
        );
    }

    public ProvaQuestaoDTO buscarQuestaoCompletaPorProvaUuidEOrdem(UUID provaUuid, Integer ordem) {
        ProvaQuestao provaQuestao = provaQuestaoRepository.findByProva_ExternalIdAndOrdem(provaUuid, ordem)
                .orElseThrow(() -> new ValidacaoException("Questão não encontrada na prova."));

        Questoes questao = provaQuestao.getQuestao();

        List<QuestaoCompletaDTO.AlternativaDTO> alternativas = questao.getAlternativas()
                .stream()
                .map(a -> new QuestaoCompletaDTO.AlternativaDTO(
                        a.getLetter(),
                        a.getText(),
                        a.getFile(),
                        a.isCorrect()
                ))
                .toList();

        QuestaoCompletaDTO questaoCompletaDTO = new QuestaoCompletaDTO(
                questao.getExternalId(),
                questao.getTitle(),
                questao.getDiscipline(),
                questao.getYear(),
                questao.getContext(),
                Arrays.asList(questao.getFiles()),
                questao.getAlternativesIntroduction(),
                alternativas
        );

        return new ProvaQuestaoDTO(
                provaQuestao.getProva().getExternalId(),
                provaQuestao.getOrdem(),
                provaQuestao.getAlternativaRespondida(),
                provaQuestao.getTempoGasto(),
                questaoCompletaDTO
        );
    }


}
