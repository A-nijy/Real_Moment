package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
