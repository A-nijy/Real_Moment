package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CartDto {


    // 장바구니 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartResponse {

        private Long cartId;
        private ItemDto.SimpleItemResponse item;
        private int stock;
    }



    // 장바구니 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartSaveRequest {

        private Long itemId;
        private int stock;
    }


    // 장바구니 수략 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartUpdateCountRequest {

        private Long cartId;
        private int stock;
    }


    // 장바구니 구매 체크 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartUpdateCheckRequest {

        private Long cartId;
    }

}
