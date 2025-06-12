package SimulaEnem.controller;

import SimulaEnem.dto.Prova.DadosParaCriarProva;
import SimulaEnem.dto.Prova.DadosProvaCriada;
import SimulaEnem.dto.questao.QuestaoCompletaDTO;
import SimulaEnem.service.ProvaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{provaId}/questao/{ordem}")
    public ResponseEntity<QuestaoCompletaDTO> buscarQuestaoPorOrdem(@PathVariable Long provaId, @PathVariable Integer ordem) {

        QuestaoCompletaDTO questao = provaService.buscarQuestaoCompletaPorProvaIdEOrdem(provaId, ordem);
        return ResponseEntity.ok(questao);
    }

}

