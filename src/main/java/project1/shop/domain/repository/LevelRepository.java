package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {
}
