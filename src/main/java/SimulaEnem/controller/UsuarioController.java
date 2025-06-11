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

import java.time.LocalDateTime;
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

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario = usuarioService.cadastrar(dados);
        return ResponseEntity.ok(usuario);
    }

    //testar pelo external id
    @GetMapping("/{externalId}")
    public ResponseEntity<Usuario> listarUsuarioPorExternalId(@PathVariable UUID externalId) {
        return usuarioRepository.findByExternalId(externalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/teste")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarDados(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findByAtivoTrue(pageable);
        Page<DadosListagemUsuarios> dtoPage = usuariosPage.map(DadosListagemUsuarios::new);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioRepository.findByAtivoTrue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return usuarioService.atualizarUsuario(id, dados);
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<?> reativar(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setAtivo(true);
            usuario.setDeletedAt(null);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(
                    String.format("Usuário %s foi reativado com sucesso. Ativo: %s", usuario.getNome(), usuario.getAtivo())
            );
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> desativar(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setAtivo(false);
            usuario.setDeletedAt(LocalDateTime.now());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(
                    String.format("Usuário " + usuario.getNome() + " foi desativado com sucesso. Ativo: " + usuario.getAtivo())
            );
        }).orElse(ResponseEntity.notFound().build());
    }

}
