package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Grade;
import rm.backend.domain.repository.custom.GradeRepositoryCustom;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {

    Optional<Grade> findByGradeName(String gradeName);

    Optional<Grade> findByGradePrice(int gradePrice);
}
