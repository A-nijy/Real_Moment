package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.dto.AddressRequestDto;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    public Address(AddressRequestDto requestDto, Member member){
        this.member = member;
        name = requestDto.getName();
        address = requestDto.getAddress();
        detAddress = requestDto.getDetAddress();
        isDefAddress = requestDto.isDefAddress();
    }

    public void update(AddressRequestDto requestDto){
        name = requestDto.getName();
        address = requestDto.getAddress();
        detAddress = requestDto.getDetAddress();
        isDefAddress = requestDto.isDefAddress();
    }
}
