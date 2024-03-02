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
        private String tel;
        private String mainAddress;
        private String detAddress;
        private boolean isDefault;
    }


    // 배송지 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest {
        private Long addressId;
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private boolean isDefault;
    }


    // 배송지 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddressResponse {
        private Long addressId;
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private boolean isDefault;



        public AddressResponse(Address address){
            addressId = address.getAddressId();
            name = address.getName();
            tel = address.getTel();
            this.mainAddress = address.getMainAddress();
            detAddress = address.getDetAddress();
            isDefault = address.isDefault();
        }
    }
}
