package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
