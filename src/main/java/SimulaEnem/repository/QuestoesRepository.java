package SimulaEnem.repository;

import SimulaEnem.domain.questoes.Questoes;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestoesRepository extends JpaRepository<Questoes, Long> {

    List<Questoes> findByYear(int year, Sort sort);

    List<Questoes> findByDiscipline(String discipline);

    List<Questoes> findByLanguage(String language);

    @Query(value = "SELECT * FROM questoes ORDER BY RANDOM() LIMIT :quantidade", nativeQuery = true)
    List<Questoes> buscarQuestoesAleatorias(@Param("quantidade") int quantidade);
}
