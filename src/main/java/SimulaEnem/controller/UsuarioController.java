package SimulaEnem.controller;

import SimulaEnem.repository.UsuarioRepository;
import SimulaEnem.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Email já está em uso.");
        }
        if (usuario.getTelefone() != null && usuarioRepository.existsByTelefone(usuario.getTelefone())) {
            return ResponseEntity.badRequest().body("Telefone já está em uso.");
        }
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioRepository.findByAtivoTrue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Usuario novoUsuario) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (novoUsuario.getNome() != null) usuario.setNome(novoUsuario.getNome());
            if (novoUsuario.getSobrenome() != null) usuario.setSobrenome(novoUsuario.getSobrenome());
            if (novoUsuario.getEmail() != null && !novoUsuario.getEmail().equals(usuario.getEmail())) {
                if (usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
                    return ResponseEntity.badRequest().body("Email já está em uso.");
                }
                usuario.setEmail(novoUsuario.getEmail());
            }
            if (novoUsuario.getTelefone() != null && !novoUsuario.getTelefone().equals(usuario.getTelefone())) {
                if (usuarioRepository.existsByTelefone(novoUsuario.getTelefone())) {
                    return ResponseEntity.badRequest().body("Telefone já está em uso.");
                }
                usuario.setTelefone(novoUsuario.getTelefone());
            }
            if (novoUsuario.getApelido() != null) usuario.setApelido(novoUsuario.getApelido());
            if (novoUsuario.getSenha() != null) usuario.setSenha(novoUsuario.getSenha());

            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desativar(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(
                    String.format("Usuário " + usuario.getNome() + " foi desativado com sucesso. Ativo: " + usuario.getAtivo())
            );
        }).orElse(ResponseEntity.notFound().build());
    }
}
