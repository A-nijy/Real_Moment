package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Order;
import project1.shop.domain.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrder(Order order);

    List<OrderDetail> findByOrder_OrderId(Long orderId);

    Optional<OrderDetail> findByOrder_OrderIdAndItem_ItemId(Long orderId, Long itemId);
}
