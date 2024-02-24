package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.OrderDetail;
import project1.shop.domain.entity.Orders;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrders(Orders orders);

    List<OrderDetail> findByOrders_OrderId(Long orderId);
}
