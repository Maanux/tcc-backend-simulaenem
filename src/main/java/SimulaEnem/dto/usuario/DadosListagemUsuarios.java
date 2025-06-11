package SimulaEnem.dto.usuario;

import SimulaEnem.domain.usuario.Usuario;

public record DadosListagemUsuarios(String nome, String sobrenome, String apelido, String email) {

    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getNome(), usuario.getSobrenome(), usuario.getApelido(), usuario.getEmail());
    }
}
