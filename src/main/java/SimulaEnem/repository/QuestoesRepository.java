package SimulaEnem.repository;

import SimulaEnem.domain.questoes.Questoes;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestoesRepository extends JpaRepository<Questoes, Long> {

    List<Questoes> findByYear(int year, Sort sort);

    List<Questoes> findByDiscipline(String discipline);

    List<Questoes> findByLanguage(String language);

}
