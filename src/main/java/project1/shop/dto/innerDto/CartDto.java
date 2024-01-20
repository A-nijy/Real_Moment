package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CartDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartResponse {

        private Long id;
        private ItemDto.SimpleItemResponse item;
        private int stock;
        private int price;
        private boolean isCheck;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartSaveRequest {

        private Long memberId;
        private Long itemId;
        private int stock;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartUpdateStockRequest {

        private Long cartId;
        private Long memberId;
        private int stock;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CartUpdateCheckRequest {

        private Long cartId;
        private Long memberId;
        private boolean isCheck;
    }

}
