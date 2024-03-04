package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminOrderService;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.dto.innerDto.SearchDto;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 주문 내역 목록 조회
    @GetMapping("/admin/orderList")
    public OrderDto.OrderPageResponse showOrders(SearchDto.OrdersSearch request){

        OrderDto.OrderPageResponse orderPageDto = adminOrderService.showOrders(request);

        return orderPageDto;
    }



    // 주문 내역 상세 조회
    @GetMapping("/admin/order")
    public OrderDto.AdminOrderDetailResponse showOrder(@RequestParam("orderId") Long orderId){

        OrderDto.AdminOrderDetailResponse orderDetailResponse = adminOrderService.showOrder(orderId);

        return orderDetailResponse;
    }



    // 주문 상태 변경하기   [ 결제준비, 결제완료, 배송준비, 배송중, 배송완료, 결제취소, 환불완료]
    @PatchMapping("/admin/order")
    public String updateOrderStatus(@RequestBody OrderDto.AdminOrderStatus request){

        adminOrderService.updateOrderStatus(request);

        return "주문 상태가 변경되었습니다.";
    }


    // 결제 취소 토큰 발급 후 바로 결제 취소하기
    @PostMapping("/admin/order/cancel")
    public String orderCancel(@RequestBody OrderDto.CancelRequest request) throws IOException {

        adminOrderService.orderCancel(request);

        return "결제 취소하기 위한 토큰 발금이 완료되었습니다.";
    }
}
