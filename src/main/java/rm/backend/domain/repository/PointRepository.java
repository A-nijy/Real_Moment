package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Point;
import rm.backend.domain.repository.custom.PointRepositoryCustom;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {
}
