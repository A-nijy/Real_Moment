package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Address;

public class AddressDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddressRequest {
        private Long Id;
        private String name;
        private String address;
        private String detAddress;
        private boolean isDefAddress;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddressResponse {
        private Long id;
        private String name;
        private String address;
        private String detAddress;
        private boolean isDefAddress;



        public AddressResponse(Address address){
            id = address.getAddressId();
            name = address.getName();
            this.address = address.getAddress();
            detAddress = address.getDetAddress();
            isDefAddress = address.isDefAddress();
        }
    }
}
