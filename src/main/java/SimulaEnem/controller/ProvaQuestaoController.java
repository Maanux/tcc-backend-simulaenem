package SimulaEnem.controller;

import SimulaEnem.dto.Prova.*;
import SimulaEnem.service.ProvaQuestaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/provas")
public class ProvaQuestaoController {
    private final ProvaQuestaoService provaQuestaoService;

    public ProvaQuestaoController(ProvaQuestaoService provaQuestaoService) {
        this.provaQuestaoService = provaQuestaoService;
    }

    @PostMapping("/{provaUuid}/questoes/{questaoUuid}/responder")
    public ResponseEntity<RespostaDTO> responderQuestao(
            @PathVariable UUID provaUuid,
            @PathVariable UUID questaoUuid,
            @RequestBody ResponderQuestaoDTO respostaRequest) {
        RespostaDTO resposta = provaQuestaoService.responderQuestao(provaUuid, questaoUuid, respostaRequest);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/{provaUuid}/pausar")
    public ResponseEntity<CheckpointProvaDTO> pausarProva(
            @PathVariable UUID provaUuid,
            @RequestBody PausarProvaDTO dados) {
        CheckpointProvaDTO checkpoint = provaQuestaoService.pausarProva(provaUuid, dados);
        return ResponseEntity.ok(checkpoint);
    }

    @PostMapping("/{provaUuid}/retomar")
    public ResponseEntity<CheckpointProvaDTO> retomarProva(@PathVariable UUID provaUuid) {
        CheckpointProvaDTO checkpoint = provaQuestaoService.retomarProva(provaUuid);
        return ResponseEntity.ok(checkpoint);
    }

    @GetMapping("/{provaUuid}/status")
    public ResponseEntity<CheckpointProvaDTO> obterStatus(@PathVariable UUID provaUuid) {
        CheckpointProvaDTO status = provaQuestaoService.obterStatusProva(provaUuid);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/{provaUuid}/finalizar")
    public ResponseEntity<ResultadoProvaDTO> finalizarProva(@PathVariable UUID provaUuid) {
        ResultadoProvaDTO resultado = provaQuestaoService.finalizarProva(provaUuid);
        return ResponseEntity.ok(resultado);
    }
}

