package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.enumeration.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Orders{

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
    private String requestText;
    private String tel;

//    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String merchantUid;
    private String impUid = null;

    private String refoundText = null;



    // 결제시 결제창 열기 전에 임시 주문 객체 생성용
    public Orders(Member member, OrderDto.PaymentRequest request){

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
        status = PaymentStatus.PAYMENT_READY;
        merchantUid = UUID.randomUUID().toString();         // 임시로 일단 랜덤 값 부여 (나중에 규칙 정하면 수정 에정)
    }


    // 아임포트로 부터 가져온 결제 번호를 저장 or 결제 상태 PAYMENT_DONE
    public void updateBySuccess(String impUid){
        this.status = PaymentStatus.PAYMENT_DONE;
        this.impUid = impUid;
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
}
