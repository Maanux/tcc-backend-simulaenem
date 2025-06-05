package SimulaEnem.repository;

import SimulaEnem.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByAtivoTrue();

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByApelido(String apelido);

    Page<Usuario> findByAtivoTrue(Pageable pageable);
}
