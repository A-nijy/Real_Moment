package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Level;
import project1.shop.domain.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
