package SimulaEnem.repository;

import SimulaEnem.domain.alternativas.Alternativas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlternativasRepository extends JpaRepository<Alternativas, Long> {

    List<Alternativas> findByQuestaoIdOrderByLetterAsc(Long id);
}
