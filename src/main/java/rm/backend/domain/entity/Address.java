package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.dto.innerDto.AddressDto;
import rm.backend.enumeration.Location;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    @Enumerated(EnumType.STRING)
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;
    private String tel;
    private String mainAddress;
    private String detAddress;
    private String zipCode;
    private boolean isDefault;

    public Address(AddressDto.SaveRequest request, Member member){
        this.member = member;
        location = Location.fromString(request.getLocation());
        name = request.getName();
        tel = request.getTel();
        mainAddress = request.getMainAddress();
        detAddress = request.getDetAddress();
        zipCode = request.getZipCode();
        isDefault = request.isDefault();
    }

    public void update(AddressDto.UpdateRequest request){
        location = Location.fromString(request.getLocation());
        name = request.getName();
        tel = request.getTel();
        mainAddress = request.getMainAddress();
        detAddress = request.getDetAddress();
        zipCode = request.getZipCode();
        isDefault = request.isDefault();
    }

    public void defaultFalse(){
        isDefault = false;
    }
}
