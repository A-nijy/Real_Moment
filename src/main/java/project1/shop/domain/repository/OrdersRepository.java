package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
