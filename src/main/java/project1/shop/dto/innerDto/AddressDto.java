package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Address;

public class AddressDto {


    // 배송지 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SaveRequest {
        private String name;
        private String address;
        private String detAddress;
        private boolean isDefAddress;
    }


    // 배송지 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest {
        private Long Id;
        private String name;
        private String address;
        private String detAddress;
        private boolean isDefAddress;
    }


    // 배송지 목록 조회 응답
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
