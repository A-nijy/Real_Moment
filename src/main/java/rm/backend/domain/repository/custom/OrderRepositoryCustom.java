package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Order;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;

public interface OrderRepositoryCustom {

    Page<Order> searchMyOrders(SearchDto.MyOrdersSearch searchDto, Long memberId, Pageable pageable);

    Page<Order> searchOrders(SearchDto.OrdersSearch searchDto, Pageable pageable);

    List<Order> findSevenDays();
}
