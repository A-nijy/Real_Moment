package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
