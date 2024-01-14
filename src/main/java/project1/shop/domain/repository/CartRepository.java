package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
