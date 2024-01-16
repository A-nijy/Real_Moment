package project1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemViewResponseDto {

    private ItemResponseDto itemResponseDto;
    private List<ReviewResponseDto> reviewResponseDto;
    private List<ItemQAResponseDto> itemQAResponseDto;

}
