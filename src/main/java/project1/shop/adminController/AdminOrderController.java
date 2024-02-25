package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminOrderService;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 주문 내역 목록 조회
    @PostMapping("/admin/orders")
    public List<OrderDto.OrderResponse> showOrders(@PathVariable Long id, @RequestBody SearchDto.OrdersSearch request){

        List<OrderDto.OrderResponse> orders = adminOrderService.showOrders(id, request);

        return orders;
    }



    // 주문 내역 상세 조회
    @GetMapping("/admin/order/detail/{id}")
    public OrderDto.AdminOrderDetailResponse showOrder(@PathVariable Long id, @RequestParam("orderId") Long orderId){

        OrderDto.AdminOrderDetailResponse orderDetailResponse = adminOrderService.showOrder(orderId);

        return orderDetailResponse;
    }
}
