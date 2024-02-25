package project1.shop.controller;


import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.dto.innerDto.PortOneDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.service.OrderService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    // 구매하기 버튼을 눌러 결제 정보 입력 창 호출 (이름 / 연락처 / 주소 / 요청사항 / 상품 / 총 상품 금액 / 총 할인 금액 / 보유 총 적립금 / 사용 적립금
    @PostMapping("/order/{id}")
    public OrderDto.OrderPageResponse giveOrderPage(@PathVariable Long id, @RequestBody List<OrderDto.OrderPageItemRequest> requestList){

        OrderDto.OrderPageResponse orderPageDto = orderService.giveOrderPage(id, requestList);

        return orderPageDto;
    }


    // 1. 결제하기 버튼을 눌러서 임시 order객체 생성 후 저장 및 이니시스 결제창에 전달할 정보 DTO 응답 (+ 원래 결제해야하는 가격과 요청받은 결제 가격이 동일한지 검증)(+ orderDetail 객체도 생성)
    @PostMapping("/payment/first/{id}")
    public PortOneDto.InicisResponse firstPayment(@PathVariable Long id, @RequestBody OrderDto.PaymentRequest request){

        PortOneDto.InicisResponse inicisDto = orderService.firstPayment(id, request);

        return inicisDto;
    }


    // 2. 결제 완료시 포트원으로 부터 imp_uid, merchant_uid를 요청 받아와서 검증 후 orders객체 완성 (+ 재고 차감)
    @PostMapping("/payment/second/{id}")
    public ResponseEntity<IamportResponse<Payment>> secondPayment(@PathVariable Long id, @RequestBody PortOneDto.InicisRequest request){

        IamportResponse<Payment> iamportResponse = orderService.validatePayment(id, request);

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }



    // 주문 내역 목록 조회
    @PostMapping("/orders/{id}")
    public List<OrderDto.OrderResponse> showOrders(@PathVariable Long id, @RequestBody SearchDto.MyOrdersSearch request){

        List<OrderDto.OrderResponse> myOrders = orderService.showOrders(id, request);

        return myOrders;
    }


    // 주문 내역 상세 조회
    @GetMapping("/order/detail/{id}")
    public OrderDto.OrderDetailResponse showOrder(@PathVariable Long id, @RequestParam("orderId") Long orderId){

        OrderDto.OrderDetailResponse orderDetailResponse = orderService.showOrder(orderId);

        return orderDetailResponse;
    }


}
