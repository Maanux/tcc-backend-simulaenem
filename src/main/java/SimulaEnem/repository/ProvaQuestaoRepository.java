package SimulaEnem.repository;

import SimulaEnem.domain.prova.ProvaQuestao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProvaQuestaoRepository extends JpaRepository<ProvaQuestao, Long> {

    Optional<ProvaQuestao> findByProvaIdAndOrdem(Long provaId, Integer ordem);

    Optional<ProvaQuestao> findByProva_ExternalIdAndOrdem(UUID externalId, Integer ordem);

    Optional<ProvaQuestao> findByProvaExternalIdAndQuestaoExternalId(UUID provaUuid, UUID questaoUuid); //pegar a questao

}
