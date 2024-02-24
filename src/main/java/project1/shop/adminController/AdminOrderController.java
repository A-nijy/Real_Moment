package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("/admin/orders/{id}")
    public List<OrderDto.OrderListResponse> showOrders(@PathVariable Long id, @RequestBody SearchDto.OrdersSearch request){

        List<OrderDto.OrderListResponse> orders = adminOrderService.showOrders(id, request);

        return orders;
    }
}
