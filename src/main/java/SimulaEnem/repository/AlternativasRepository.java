package SimulaEnem.repository;

import SimulaEnem.domain.alternativas.Alternativas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativasRepository extends JpaRepository<Alternativas, Long> {
}
