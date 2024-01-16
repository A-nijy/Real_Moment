package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
