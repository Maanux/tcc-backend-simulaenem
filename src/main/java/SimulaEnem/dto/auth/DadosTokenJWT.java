package SimulaEnem.dto.auth;

import java.util.UUID;

public record DadosTokenJWT(
        String token,
        String type,
        UUID uuid,
        String email,
        String nome,
        String apelido
) {
    public DadosTokenJWT(String token, UUID uuid, String email, String nome, String apelido) {
        this("Bearer", token, uuid, email, nome, apelido);
    }

    public DadosTokenJWT( String token, String type, UUID uuid, String email, String nome, String apelido) {
        this.type = type;
        this.token = token;
        this.uuid = uuid;
        this.email = email;
        this.nome = nome;
        this.apelido = apelido;
    }
}
