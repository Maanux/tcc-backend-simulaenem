package SimulaEnem.repository;

import SimulaEnem.domain.prova.ProvaQuestao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvaQuestaoRepository extends JpaRepository<ProvaQuestao, Long> {

    Optional<ProvaQuestao> findByProvaIdAndOrdem(Long provaId, Integer ordem);

    Optional<ProvaQuestao> findByProva_ExternalIdAndOrdem(UUID externalId, Integer ordem);

    Optional<ProvaQuestao> findByProvaExternalIdAndQuestaoExternalId(UUID provaUuid, UUID questaoUuid);

    List<ProvaQuestao> findByProva_ExternalId(UUID externalId);

    long countByProvaExternalId(UUID provaExternalId);

    List<ProvaQuestao> findAllByProvaExternalId(UUID provaExternalId);


}
