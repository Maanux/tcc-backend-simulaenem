package SimulaEnem.controller;

import SimulaEnem.dto.Prova.DadosParaCriarProva;
import SimulaEnem.dto.Prova.DadosProvaCriada;
import SimulaEnem.dto.Prova.ProvaQuestaoDTO;
import SimulaEnem.service.ProvaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/provas")
@RequiredArgsConstructor
public class ProvaController {

    @Autowired
    private final ProvaService provaService;

    @PostMapping("/criar")
    public ResponseEntity<DadosProvaCriada> criarProva(@RequestBody DadosParaCriarProva dadosParaCriarProva) {
        DadosProvaCriada provaCriada = provaService.criarProvaAleatoria(dadosParaCriarProva.externalId(), dadosParaCriarProva.quantidadeQuestoes());
        return  ResponseEntity.ok(provaCriada);
    }

    @GetMapping("/{provaUuid}/questao/{ordem}")
    public ResponseEntity<ProvaQuestaoDTO> buscarQuestaoPorOrdem(
            @PathVariable UUID provaUuid,
            @PathVariable Integer ordem) {

        ProvaQuestaoDTO questao = provaService.buscarQuestaoCompletaPorProvaUuidEOrdem(provaUuid, ordem);
        return ResponseEntity.ok(questao);
    }


}

