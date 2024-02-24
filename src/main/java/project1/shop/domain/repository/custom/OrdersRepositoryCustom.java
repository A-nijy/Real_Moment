package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Orders;
import project1.shop.dto.innerDto.SearchDto;

public interface OrdersRepositoryCustom {

    Page<Orders> searchMyOrders(SearchDto.MyOrdersSearch searchDto, Long memberId, Pageable pageable);

    Page<Orders> searchOrders(SearchDto.OrdersSearch searchDto, Pageable pageable);
}
