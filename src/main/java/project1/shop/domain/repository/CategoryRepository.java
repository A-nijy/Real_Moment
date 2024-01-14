package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
