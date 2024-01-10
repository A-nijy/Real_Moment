package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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
    private String status;
    private String refoundText;
}
