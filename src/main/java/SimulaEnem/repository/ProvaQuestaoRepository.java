package SimulaEnem.repository;

import SimulaEnem.domain.prova.ProvaQuestao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProvaQuestaoRepository extends JpaRepository<ProvaQuestao, Long> {

    Optional<ProvaQuestao> findByProvaIdAndOrdem(Long provaId, Integer ordem);

}
