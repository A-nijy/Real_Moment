package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.enumeration.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderedDate;
    private LocalDateTime shippedDate;
    private int price;
    private String name;
    private String address;
    private String detAddress;
    private String request;
    private String tel;

//    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String merchantUid;
    private String impUid;

    private String refoundText;
}
