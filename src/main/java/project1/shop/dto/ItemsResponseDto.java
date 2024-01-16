package project1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.entity.Item;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemsResponseDto {

    private Long id;
    private String name;
    private int price;
    private int discountRate;
    private int sellPrice;
    private boolean isSellCheck;
    private String mainImg;



    public ItemsResponseDto (Item item){
        id = item.getItemId();
        name = item.getName();
        price = item.getPrice();
        discountRate = item.getDiscountRate();
        sellPrice = item.getSellPrice();
        isSellCheck = item.isSellCheck();
        mainImg = item.getMainImg();
    }

}
