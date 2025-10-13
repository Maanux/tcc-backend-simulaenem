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

//    @PostMapping
//    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
//        var usuario = usuarioService.cadastrar(dados);
//        return ResponseEntity.ok(usuario);
//    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return usuarioService.atualizarUsuario(id, dados);
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<?> reativar(@PathVariable Long id) {
        return usuarioService.reativar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desativar(@PathVariable Long id) {
        return usuarioService.desativar(id);
    }

}
