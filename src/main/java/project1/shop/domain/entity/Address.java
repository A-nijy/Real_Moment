package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.dto.innerDto.AddressDto;


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
    private String tel;
    private String mainAddress;
    private String detAddress;
    private boolean isDefAddress;

    public Address(AddressDto.SaveRequest request, Member member){
        this.member = member;
        name = request.getName();
        tel = request.getTel();
        mainAddress = request.getMainAddress();
        detAddress = request.getDetAddress();
        isDefAddress = request.isDefAddress();
    }

    public void update(AddressDto.UpdateRequest request){
        name = request.getName();
        tel = request.getTel();
        mainAddress = request.getMainAddress();
        detAddress = request.getDetAddress();
        isDefAddress = request.isDefAddress();
    }
}
