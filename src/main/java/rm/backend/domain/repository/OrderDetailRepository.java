package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Order;
import rm.backend.domain.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrder(Order order);

    List<OrderDetail> findByOrder_OrderId(Long orderId);

    Optional<OrderDetail> findByOrder_OrderIdAndItem_ItemId(Long orderId, Long itemId);
}
