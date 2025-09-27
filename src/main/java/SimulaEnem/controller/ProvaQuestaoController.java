package SimulaEnem.controller;

import SimulaEnem.dto.Prova.ResponderQuestaoDTO;
import SimulaEnem.dto.Prova.RespostaDTO;
import SimulaEnem.service.ProvaQuestaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/provas")
public class ProvaQuestaoController {
    private final ProvaQuestaoService provaQuestaoService;

    public ProvaQuestaoController (ProvaQuestaoService provaQuestaoService) {
        this.provaQuestaoService = provaQuestaoService;
    }

    @PostMapping("/{provaUuid}/questoes/{questaoUuid}/responder")
    public ResponseEntity<RespostaDTO> responderQuestao(
            @PathVariable UUID provaUuid,
            @PathVariable UUID questaoUuid,
            @RequestBody ResponderQuestaoDTO respostaRequest
            ){
        RespostaDTO resposta = provaQuestaoService.responderQuestao(provaUuid,questaoUuid,respostaRequest);
        return ResponseEntity.ok(resposta);
    }
}
