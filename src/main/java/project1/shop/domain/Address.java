package project1.shop.domain;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;
    private String address;
    private String detAddress;
    private boolean isDefAddress;
}
