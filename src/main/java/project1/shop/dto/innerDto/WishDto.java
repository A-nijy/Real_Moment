package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WishDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WishListResponse {

        private Long id;
        private ItemDto.SimpleItemResponse item;


    }
}
