package SimulaEnem.repository;

import SimulaEnem.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByAtivoTrue();

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

}
