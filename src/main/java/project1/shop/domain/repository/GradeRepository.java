package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.repository.custom.GradeRepositoryCustom;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {

    Optional<Grade> findByGradeName(String gradeName);

    Optional<Grade> findByGradePrice(int gradePrice);
}
