package project1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequestDto {

    private Long addressId;
    private String name;
    private String address;
    private String detAddress;
    private boolean isDefAddress;
}
