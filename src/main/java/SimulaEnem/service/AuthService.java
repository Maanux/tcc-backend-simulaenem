package SimulaEnem.service;

import SimulaEnem.domain.ValidacaoException;
import SimulaEnem.domain.usuario.Usuario;
import SimulaEnem.dto.auth.DadosLogin;
import SimulaEnem.dto.auth.DadosTokenJWT;
import SimulaEnem.dto.usuario.DadosCadastroUsuario;
import SimulaEnem.repository.UsuarioRepository;
import SimulaEnem.infra.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    public DadosTokenJWT register(DadosCadastroUsuario dados) {
        Usuario usuario = usuarioService.cadastrar(dados);

        String token = jwtUtil.generateToken(
                usuario.getEmail(),
                usuario.getExternalId(),
                usuario.getApelido()
        );

        return new DadosTokenJWT(
                token,
                usuario.getExternalId(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getApelido()
        );
    }

    public DadosTokenJWT login(DadosLogin dados) {
        Usuario usuario = usuarioRepository.findByEmail(dados.email())
                .orElseThrow(() -> new ValidacaoException("Email ou senha incorretos"));

        if (!usuario.getAtivo()) {
            throw new ValidacaoException("Usuário inativo");
        }

        if (!passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
            throw new ValidacaoException("Email ou senha incorretos");
        }

        String token = jwtUtil.generateToken(
                usuario.getEmail(),
                usuario.getExternalId(),
                usuario.getApelido()
        );

        return new DadosTokenJWT(
                token,
                usuario.getExternalId(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getApelido()
        );
    }
}
