package SimulaEnem.service;

import SimulaEnem.domain.ValidacaoException;
import SimulaEnem.domain.usuario.Usuario;
import SimulaEnem.dto.usuario.DadosAtualizacaoUsuario;
import SimulaEnem.dto.usuario.DadosCadastroUsuario;
import SimulaEnem.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(DadosCadastroUsuario dados) {
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new ValidacaoException("Email já está em uso.");
        }

        if (dados.telefone() != null && usuarioRepository.existsByTelefone(dados.telefone())) {
            throw new ValidacaoException("Telefone já está em uso.");
        }

        if (usuarioRepository.existsByApelido(dados.apelido())) {
            throw new ValidacaoException("Apelido já está em uso.");
        }

        var usuario = Usuario.builder()
                .nome(dados.nome())
                .sobrenome(dados.sobrenome())
                .email(dados.email())
                .telefone(dados.telefone())
                .apelido(dados.apelido())
                .senha(dados.senha())
                .ativo(true)
                .build();
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<?> atualizarUsuario(Long id, DadosAtualizacaoUsuario dados) {
        return usuarioRepository.findById(id).map(usuario -> {

            if (dados.nome() != null) usuario.setNome(dados.nome());
            if (dados.sobrenome() != null) usuario.setSobrenome(dados.sobrenome());

            if (dados.email() != null && !dados.email().equals(usuario.getEmail())) {
                if (usuarioRepository.existsByEmail(dados.email())) {
                    return ResponseEntity.badRequest().body("Email já está em uso.");
                }
                usuario.setEmail(dados.email());
            }

            if (dados.telefone() != null && !dados.telefone().equals(usuario.getTelefone())) {
                if (usuarioRepository.existsByTelefone(dados.telefone())) {
                    return ResponseEntity.badRequest().body("Telefone já está em uso.");
                }
                usuario.setTelefone(dados.telefone());
            }

            if (dados.apelido() != null && !dados.apelido().equals(usuario.getApelido())) {
                if (usuarioRepository.existsByApelido(dados.apelido())) {
                    return ResponseEntity.badRequest().body("Apelido já está em uso.");
                }
                usuario.setApelido(dados.apelido());
            }

            if (dados.senha() != null) usuario.setSenha(dados.senha());

            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        }).orElse(ResponseEntity.notFound().build());
    }
}
