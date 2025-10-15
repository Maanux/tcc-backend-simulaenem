package SimulaEnem.repository;

import SimulaEnem.domain.prova.Prova;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvasRepository  extends JpaRepository<Prova, Long> {

    List<Prova> findByUsuarioExternalId(UUID usuarioExternalId);

    Page<Prova> findByUsuarioExternalId(UUID usuarioExternalId, Pageable pageable);

    Optional<Prova> findByExternalIdAndUsuarioExternalId(UUID provaExternalId, UUID usuarioExternalId);

}
