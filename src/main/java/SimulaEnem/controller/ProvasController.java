package SimulaEnem.controller;

import java.util.List;
import java.util.UUID;

import SimulaEnem.dto.Prova.ProvasDTO;
import SimulaEnem.service.ProvasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/provas")
public class ProvasController {

    private final ProvasService provaService;

    public ProvasController(ProvasService provaService) {
        this.provaService = provaService;
    }

    @GetMapping("/usuario/{usuarioUuid}")
    public ResponseEntity<List<ProvasDTO>> buscarPorUsuario(
            @PathVariable("usuarioUuid") UUID usuarioUuid) {
        var lista = provaService.buscarPorUsuarioUuid(usuarioUuid);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{usuarioUuid}/page")
    public ResponseEntity<Page<ProvasDTO>> buscarPorUsuarioPaged(
            @PathVariable("usuarioUuid") UUID usuarioUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size);
        var resultado = provaService.buscarPorUsuarioUuid(usuarioUuid, pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{provaUuid}/usuario/{usuarioUuid}")
    public ResponseEntity<ProvasDTO> buscarProvaDoUsuario(
            @PathVariable UUID provaUuid,
            @PathVariable UUID usuarioUuid) {
        var dto = provaService.buscarPorProvaEUsuario(provaUuid, usuarioUuid);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}
