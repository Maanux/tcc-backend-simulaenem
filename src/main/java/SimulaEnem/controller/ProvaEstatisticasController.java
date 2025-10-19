package SimulaEnem.controller;

import SimulaEnem.dto.Prova.*;
import SimulaEnem.service.EstatisticasProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/provas")
public class ProvaEstatisticasController {

    @Autowired
    private EstatisticasProvaService estatisticasService;

    @GetMapping("/{provaUuid}/estatisticas")
    public ResponseEntity<EstatisticasProvaDTO> obterEstatisticasGerais(
            @PathVariable UUID provaUuid) {
        EstatisticasProvaDTO stats = estatisticasService.obterEstatisticasGerais(provaUuid);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{provaUuid}/estatisticas/disciplinas")
    public ResponseEntity<List<EstatisticasDisciplinaDTO>> obterEstatisticasPorDisciplina(
            @PathVariable UUID provaUuid) {
        List<EstatisticasDisciplinaDTO> stats =
                estatisticasService.obterEstatisticasPorDisciplina(provaUuid);
        return ResponseEntity.ok(stats);
    }
}
