package SimulaEnem.domain.usuario;

import SimulaEnem.domain.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefone;

    private String apelido;
    private String senha;

    private Boolean ativo = true;

    @PrePersist
    public void prePersist() {
         setCreatedAt(LocalDateTime.now());
        if (getExternalId() == null) {
            setExternalId(UUID.randomUUID());
        }
    }

    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

    public void deletar() {
        setAtivo(false);
        setDeletedAt(LocalDateTime.now());
    }

}
