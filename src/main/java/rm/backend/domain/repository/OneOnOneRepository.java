package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.OneOnOne;
import rm.backend.domain.repository.custom.OneOnOneRepositoryCustom;

public interface OneOnOneRepository extends JpaRepository<OneOnOne, Long>, OneOnOneRepositoryCustom {
}
