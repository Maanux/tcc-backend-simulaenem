package SimulaEnem.service;

import SimulaEnem.domain.ValidacaoException;
import SimulaEnem.domain.usuario.Usuario;
import SimulaEnem.dto.usuario.DadosAtualizacaoUsuario;
import SimulaEnem.dto.usuario.DadosCadastroUsuario;
import SimulaEnem.dto.usuario.DadosListagemUsuarios;
import SimulaEnem.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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
                .senha(passwordEncoder.encode(dados.senha()))
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

            if (dados.senha() != null) {
                usuario.setSenha(passwordEncoder.encode(dados.senha()));
            }

            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> reativar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setAtivo(true);
            usuario.setDeletedAt(null);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(
                    String.format("Usuário %s foi reativado com sucesso. Ativo: %s", usuario.getNome(), usuario.getAtivo())
            );
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> desativar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setAtivo(false);
            usuario.setDeletedAt(LocalDateTime.now());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(
                    String.format("Usuário %s foi desativado com sucesso. Ativo: %s", usuario.getNome(), usuario.getAtivo())
            );
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Usuario> buscarPorExternalId(UUID externalId) {
        return usuarioRepository.findByExternalId(externalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Usuario>> listarAtivos() {
        return ResponseEntity.ok(usuarioRepository.findByAtivoTrue());
    }

    public ResponseEntity<Page<DadosListagemUsuarios>> listarDadosPaginados(Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findByAtivoTrue(pageable);
        Page<DadosListagemUsuarios> dtoPage = usuariosPage.map(DadosListagemUsuarios::new);
        return ResponseEntity.ok(dtoPage);
    }
}