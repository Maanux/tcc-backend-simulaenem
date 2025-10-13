package SimulaEnem.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Sobrenome é obrigatório")
        String sobrenome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Pattern(
                regexp = "^\\d{2}9\\d{8}$",
                message = "Telefone deve seguir o padrão DDD + 9 + número, ex: 42999999999"
        )
        String telefone,

        @NotBlank(message = "Apelido é obrigatório")
        String apelido,

        @NotBlank(message = "Senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com ao menos uma letra maiúscula, um número e um caractere especial"
        )
        String senha
) {
}
