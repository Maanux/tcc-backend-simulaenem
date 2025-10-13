package SimulaEnem.repository;

import SimulaEnem.domain.prova.Prova;
import SimulaEnem.domain.prova.ProvaQuestao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvaRepository extends JpaRepository<Prova, Long> {

    Optional<Prova> findByExternalId(UUID externalId);

}
