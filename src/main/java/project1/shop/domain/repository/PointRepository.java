package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Point;
import project1.shop.domain.repository.custom.PointRepositoryCustom;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {
}
