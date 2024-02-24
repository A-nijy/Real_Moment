package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.OrderDetail;
import project1.shop.domain.entity.Orders;
import project1.shop.domain.repository.OrderDetailRepository;
import project1.shop.domain.repository.OrdersRepository;
import project1.shop.dto.innerDto.OrderDetailDto;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;



    @Transactional
    public List<OrderDto.OrderListResponse> showOrders(Long id, SearchDto.OrdersSearch request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        log.info("1");
        Page<Orders> orders = ordersRepository.searchOrders(request, pageRequest);
        log.info("2");
        List<OrderDto.OrderListResponse> ordersDto = orders.stream()
                .map(OrderDto.OrderListResponse::new)
                .collect(Collectors.toList());

        log.info("3");
        for (OrderDto.OrderListResponse orderDto : ordersDto){
            log.info("4");
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(orderDto.getOrderId());
            log.info("5");
            List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                    .map(OrderDetailDto.response::new)
                    .collect(Collectors.toList());
            log.info("6");
            orderDto.plusOrderDetails(orderDetailsDto);
        }
        log.info("7");
        return ordersDto;
    }
}
