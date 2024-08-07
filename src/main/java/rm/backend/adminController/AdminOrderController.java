package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.adminService.AdminOrderService;
import rm.backend.dto.innerDto.OrderDto;
import rm.backend.dto.innerDto.SearchDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 주문 내역 목록 조회
    @GetMapping("/admin/orderList/view")
    public OrderDto.OrderPageResponse showOrders(SearchDto.OrdersSearch request){

        log.info("진입");
        if (request.getLastDate().equals("")){
            log.info("빈 문자열");
        } else if (request.getLastDate() == null){
            log.info("null");
        } else {
            log.info("기타");
        }

        OrderDto.OrderPageResponse orderPageDto = adminOrderService.showOrders(request);

        return orderPageDto;
    }



    // 주문 내역 상세 조회
    @GetMapping("/admin/order/view")
    public OrderDto.AdminOrderDetailResponse showOrder(@RequestParam("orderId") Long orderId){

        OrderDto.AdminOrderDetailResponse orderDetailResponse = adminOrderService.showOrder(orderId);

        return orderDetailResponse;
    }



    // 주문 상태 변경하기   [결제완료, 배송준비, 배송중, 배송완료]
    @PatchMapping("/admin/order")
    public String updateOrderStatus(@RequestBody OrderDto.AdminOrderStatus request){

        adminOrderService.updateOrderStatus(request);

        return "주문 상태가 변경되었습니다.";
    }


    // 결제 취소 토큰 발급 후 바로 결제 취소하기
    @PostMapping("/admin/order/cancel")
    public String orderCancel(@RequestBody OrderDto.CancelRequest request) throws IOException {

        adminOrderService.orderCancel(request);

        return "결제가 취소되었습니다.";
    }

    // 환불하기
    @PostMapping("/admin/order/refund")
    public String orderRefund(@RequestBody OrderDto.CancelRequest request) throws IOException {

        adminOrderService.orderRefund(request);

        return "환불되었습니다.";
    }
}
