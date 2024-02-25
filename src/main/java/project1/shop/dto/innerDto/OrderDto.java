package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Orders;
import project1.shop.enumeration.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {


    // 주문 페이지에 이동할 때 구매할 상품과 구매할 개수 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderPageItemRequest {

        private Long itemId;
        private int count;
    }


    // 결제 버튼을 눌렀을 때 해당 주문 페이지에 작성한 데이터들을 가지고 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PaymentRequest{

        private String name;                            // 이름
        private String tel;                             // 전화번호
        private String mainAddress;                     // 메인 주소
        private String detAddress;                      // 세부 주소

        private String requestText;                     // 요청 사항

        private List<OrderPageItemRequest> items;       // 구매 상품 목록

        private int totalPrice;                         // 총 정가
        private int totalDiscountPrice;                 // 총 할인 금액
        private int usePoint;                           // 사용할 포인트
        private int getPoint;                           // 적립금
        private int buyPrice;                           // 최종 구매 금액
    }



    // 주문 페이지에 이동할 때 기본 세팅할 정보 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderPageResponse{
        private AddressDto.OrderAddressResponse orderAddress;                   // 주소에 적힌 이름, 연락처, 주소
        private List<ItemDto.OrderPageItemResponse> orderPageItemList;          // 심플한 상품 정보
        private OrderDto.PriceAndPointResponse orderPrice;                      // 총 가격 정보 및 포인트 정보

    }


    // 주문 페이지에 이동할 때 총 가격들과 포인트에 대한 정보 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PriceAndPointResponse{
        private int totalPrice = 0;                                                 // 총 정가
        private int totalDiscountPrice = 0;                                         // 총 할인 금액
        private int totalSellPrice = 0;                                             // 최종 결제할 금액
        private int point = 0;                                                      // 보유 적립금
        private int getPoint = 0;                                                   // 적립 예정 금액


        public void plusTotalPrice(int price){

            totalPrice += price;
        }

        public void plusTotalDiscountPrice(int price){

            totalDiscountPrice += price;
        }

        public void autoSetting(Member member) {
            totalSellPrice = totalPrice - totalDiscountPrice;
            point = member.getPoint();
            getPoint = (int)(totalSellPrice * (member.getGrade().getRewardRate() * 0.01));
        }
    }


    // 주문 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderResponse {

        private Long orderId;
        private LocalDateTime orderedDate;
        private int buyPrice;
        private String mainAddress;
        private String detAddress;
        private String requestText;
        private String tel;
        private PaymentStatus status;
        private String merchantUid;

        private List<OrderDetailDto.response> orderDetails;


        public OrderResponse(Orders orders){
            orderId = orders.getOrderId();
            orderedDate = orders.getOrderedDate();
            buyPrice = orders.getBuyPrice();
            mainAddress = orders.getMainAddress();
            detAddress = orders.getDetAddress();
            requestText = orders.getRequestText();
            tel = orders.getTel();
            status = orders.getStatus();
            merchantUid = orders.getMerchantUid();
        }


        public void plusOrderDetails(List<OrderDetailDto.response> orderDetails){

            this.orderDetails = orderDetails;
        }
    }


    // 주문 상세 데이터 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderDetailResponse{
        private OrderResponse orderResponse;
        private int totalPrice;
        private int totalDiscountPrice;
        private int usePoint;
        private int getPoint;
        private int buyPrice;


        public OrderDetailResponse(OrderResponse orderResponse, Orders orders){
            this.orderResponse = orderResponse;
            totalPrice = orders.getTotalPrice();
            totalDiscountPrice = orders.getTotalDiscountPrice();
            usePoint = orders.getUsePoint();
            getPoint = orders.getGetPoint();
            buyPrice = orders.getBuyPrice();
        }
    }



    // ------------------------------------------------------------

    // 주문 목록 조회 응답 (관리자용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminOrderResponse {

        private Long orderId;
        private String loginId;
        private LocalDateTime orderedDate;
        private int buyPrice;
        private String mainAddress;
        private String detAddress;
        private String requestText;
        private String tel;
        private PaymentStatus status;
        private String merchantUid;

        private List<OrderDetailDto.response> orderDetails;


        public AdminOrderResponse(Orders orders){
            orderId = orders.getOrderId();
            loginId = orders.getMember().getLoginId();
            orderedDate = orders.getOrderedDate();
            buyPrice = orders.getBuyPrice();
            mainAddress = orders.getMainAddress();
            detAddress = orders.getDetAddress();
            requestText = orders.getRequestText();
            tel = orders.getTel();
            status = orders.getStatus();
            merchantUid = orders.getMerchantUid();
        }


        public void plusOrderDetails(List<OrderDetailDto.response> orderDetails){

            this.orderDetails = orderDetails;
        }
    }


    // 주문 상세 데이터 응답 (관리자용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminOrderDetailResponse{
        private AdminOrderResponse adminOrderResponse;
        private int totalPrice;
        private int totalDiscountPrice;
        private int usePoint;
        private int getPoint;
        private int buyPrice;


        public AdminOrderDetailResponse(AdminOrderResponse adminOrderResponse, Orders orders){
            this.adminOrderResponse = adminOrderResponse;
            totalPrice = orders.getTotalPrice();
            totalDiscountPrice = orders.getTotalDiscountPrice();
            usePoint = orders.getUsePoint();
            getPoint = orders.getGetPoint();
            buyPrice = orders.getBuyPrice();
        }
    }


    // 주문 상태 변경 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminOrderStatus{
        private Long orderId;
        private String status;
    }
}
