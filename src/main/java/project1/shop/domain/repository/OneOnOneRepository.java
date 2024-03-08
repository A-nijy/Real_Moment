package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.OneOnOne;
import project1.shop.domain.repository.custom.OneOnOneRepositoryCustom;

public interface OneOnOneRepository extends JpaRepository<OneOnOne, Long>, OneOnOneRepositoryCustom {
}
