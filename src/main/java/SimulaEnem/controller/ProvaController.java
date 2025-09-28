package SimulaEnem.controller;

import SimulaEnem.dto.Prova.*;
import SimulaEnem.service.ProvaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/provas")
@RequiredArgsConstructor
public class ProvaController {

    @Autowired
    private final ProvaService provaService;

    @PostMapping("/criaraleatoria")
    public ResponseEntity<DadosProvaCriada> criarProva(@RequestBody DadosParaCriarProva dadosParaCriarProva) {
        DadosProvaCriada provaCriada = provaService.criarProvaAleatoria(dadosParaCriarProva.externalId(), dadosParaCriarProva.quantidadeQuestoes());
        return  ResponseEntity.ok(provaCriada);
    }

    @PostMapping("/criarpordisciplina")
    public ResponseEntity<DadosProvaCriada> criarProvaPorDisciplina(
            @RequestBody DadosParaCriarProvaPorDisciplina dados) {
        DadosProvaCriada provaCriada = provaService.criarProvaPorDisciplina(dados);
        return ResponseEntity.ok(provaCriada);
    }

    @GetMapping("/{provaUuid}/questao/{ordem}")
    public ResponseEntity<ProvaQuestaoDTO> buscarQuestaoPorOrdem(
            @PathVariable UUID provaUuid,
            @PathVariable Integer ordem) {

        ProvaQuestaoDTO questao = provaService.buscarQuestaoCompletaPorProvaUuidEOrdem(provaUuid, ordem);
        return ResponseEntity.ok(questao);
    }

    @GetMapping("/{provaExternalId}/questoes")
    public List<QuestaoResolvidaDTO> listarQuestoesDaProva(@PathVariable UUID provaExternalId) {
        return provaService.listarQuestoesDaProva(provaExternalId);
    }



}

