package project1.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.Item;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemResponseDto {

    private Long id;
    private String name;
    private String content;
    private int price;
    private int discountRate;
    private int sellPrice;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int stock;
    private boolean isSellCheck;
    private boolean isDeleteCheck;
    private String mainImg;
    private String serveImg;


    public ItemResponseDto(Item item){
        id = item.getItemId();
        name = item.getName();
        content = item.getContent();
        price = item.getPrice();
        discountRate = item.getDiscountRate();
        sellPrice = item.getSellPrice();
        createdDate = item.getCreatedDate();
        modifiedDate = item.getModifiedDate();
        stock = item.getStock();
        isSellCheck = item.isSellCheck();
        isDeleteCheck = item.isDeleteCheck();
        mainImg = item.getMainImg();
        serveImg = item.getServeImg();
    }
}
