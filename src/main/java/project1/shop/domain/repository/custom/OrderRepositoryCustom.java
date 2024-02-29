package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Order;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

public interface OrderRepositoryCustom {

    Page<Order> searchMyOrders(SearchDto.MyOrdersSearch searchDto, Long memberId, Pageable pageable);

    Page<Order> searchOrders(SearchDto.OrdersSearch searchDto, Pageable pageable);

    List<Order> findSevenDays();
}
