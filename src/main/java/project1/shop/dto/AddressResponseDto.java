package project1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.entity.Address;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressResponseDto {

    private Long id;
    private String name;
    private String address;
    private String detAddress;
    private boolean isDefAddress;



    public AddressResponseDto(Address address){
        id = address.getAddressId();
        name = address.getName();
        this.address = address.getAddress();
        detAddress = address.getDetAddress();
        isDefAddress = address.isDefAddress();
    }
}
