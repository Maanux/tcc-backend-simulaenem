package SimulaEnem.controller;

import SimulaEnem.dto.usuario.DadosAtualizacaoUsuario;
import SimulaEnem.dto.usuario.DadosCadastroUsuario;
import SimulaEnem.dto.usuario.DadosListagemUsuarios;
import SimulaEnem.repository.UsuarioRepository;
import SimulaEnem.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import SimulaEnem.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{externalId}")
    public ResponseEntity<Usuario> listarUsuarioPorExternalId(@PathVariable UUID externalId) {
        return usuarioService.buscarPorExternalId(externalId);
    }

    @GetMapping("/teste")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarDados(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return usuarioService.listarDadosPaginados(pageable);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return usuarioService.listarAtivos();
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<?> atualizar(@PathVariable UUID externalId, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return usuarioService.atualizarUsuario(externalId, dados);
    }

    @PutMapping("/reativar/{externalId}")
    public ResponseEntity<?> reativar(@PathVariable UUID externalId) {
        return usuarioService.reativar(externalId);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<?> desativar(@PathVariable UUID externalId) {
        return usuarioService.desativar(externalId);
    }

}
