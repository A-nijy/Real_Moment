package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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


    // 찜 목록 조회 응답 (페이지 포함)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WishListPageResponse {

        List<WishListResponse> wishList;
        private int totalPage;
        private int nowPage;
    }



    // 찜 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WishRequest {

        private Long itemId;
    }
}
