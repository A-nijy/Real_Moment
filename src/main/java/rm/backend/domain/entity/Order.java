package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.component.FieldData;
import rm.backend.dto.innerDto.OrderDto;
import rm.backend.enumeration.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderedDate;
    private LocalDateTime deliveryDate;
    private int totalPrice;
    private int totalDiscountPrice;
    private int usePoint;
    private int getPoint;
    private int buyPrice;
    private String name;
    private String mainAddress;
    private String detAddress;
    private String zipCode;
    private String requestText = null;
    private String tel;

//    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String merchantUid = null;
    private String impUid = null;

    private String reasonText = null;



    // 결제시 결제창 열기 전에 임시 주문 객체 생성용
    public Order(Member member, OrderDto.PaymentRequest request, int orderCount){

        this.member = member;
        totalPrice = request.getTotalPrice();
        totalDiscountPrice = request.getTotalDiscountPrice();
        usePoint = request.getUsePoint();
        getPoint = request.getGetPoint();
        buyPrice = request.getBuyPrice();
        name = request.getName();
        mainAddress = request.getMainAddress();
        detAddress = request.getDetAddress();
        requestText = request.getRequestText();
        tel = request.getTel();
        zipCode = request.getZipCode();
        status = PaymentStatus.PAYMENT_READY;
        merchantUid = orderNumber(orderCount);         // 임시로 일단 랜덤 값 부여 (나중에 규칙 정하면 수정 에정)
    }


    // 아임포트로 부터 가져온 결제 번호를 저장 or 결제 상태 PAYMENT_DONE
    public void updateBySuccess(String impUid){
        this.status = PaymentStatus.PAYMENT_DONE;
        this.impUid = impUid;
    }

    // 주문번호 생성후 반환
    public String orderNumber(int orderCount) {

        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = date.format(formatter);

        String number = String.format("%08d", orderCount);

        return today + number;
    }


    // 주문일 정의
    public void updateOrderedDate(){
        orderedDate = LocalDateTime.now();
    }

    // 배송 완료일 정의
    public void updateDeliveryDate(){
        deliveryDate = LocalDateTime.now();
    }


    // 주문 상태 변경
    public void updateStatus(PaymentStatus status){

        this.status = status;
    }


    // 결제 취소 or 환불 사유 추가
    public void updateReasonText(String reasonText){
        this.reasonText = reasonText;
    }
}
