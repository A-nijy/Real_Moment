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
import project1.shop.enumeration.PaymentStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;



    // 주문 목록 조회
    @Transactional
    public List<OrderDto.OrderResponse> showOrders(Long id, SearchDto.OrdersSearch request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        log.info("1");
        Page<Orders> orders = ordersRepository.searchOrders(request, pageRequest);
        log.info("2");
        List<OrderDto.OrderResponse> ordersDto = orders.stream()
                .map(OrderDto.OrderResponse::new)
                .collect(Collectors.toList());

        log.info("3");
        for (OrderDto.OrderResponse orderDto : ordersDto){
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


    // 주문 상세 조회
    @Transactional
    public OrderDto.AdminOrderDetailResponse showOrder(Long orderId) {

        Orders orders = ordersRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        OrderDto.AdminOrderResponse orderResponse = new OrderDto.AdminOrderResponse(orders);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders(orders);

        List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                .map(OrderDetailDto.response::new)
                .collect(Collectors.toList());

        orderResponse.plusOrderDetails(orderDetailsDto);



        OrderDto.AdminOrderDetailResponse orderDetailResponse = new OrderDto.AdminOrderDetailResponse(orderResponse, orders);

        return orderDetailResponse;
    }


    // 주문 상태 변경하기 [ 결제준비, 결제완료, 배송준비, 배송중, 배송완료, 결제취소, 환불요청, 환불완료 ]
    @Transactional
    public void updateOrderStatus(OrderDto.AdminOrderStatus request) {

        Orders orders = ordersRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        PaymentStatus status = PaymentStatus.fromString(request.getStatus());

        orders.updateStatus(status);
    }
}
