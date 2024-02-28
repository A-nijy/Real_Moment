package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WishDto {


    // 찜 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WishListResponse {

        private Long wishId;
        private ItemDto.SimpleItemResponse item;

    }



    // 찜 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WishRequest {

        private Long wishId;
    }
}
