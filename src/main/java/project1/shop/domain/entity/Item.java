package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeAndByCheck;
import project1.shop.dto.innerDto.ItemDto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item extends TimeAndByCheck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
    private String content;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int sellPrice;
    private int stock;
    private boolean isSellCheck;
    private boolean isDeleteCheck = false;
    private String mainImg;
    private String serveImg;



    public Item (Category category, ItemDto.SaveRequest request){
        this.category = category;
        name = request.getName();
        content = request.getContent();
        price = request.getPrice();
        discountRate = request.getDiscountRate();
        discountPrice = request.getDiscountPrice();
        sellPrice = request.getSellPrice();
        stock = request.getStock();
        isSellCheck = request.isSellCheck();
        mainImg = request.getMainImg();
        serveImg = request.getServeImg();
    }



    public void update(Category category, ItemDto.UpdateRequest request){
        this.category = category;
        name = request.getName();
        content = request.getContent();
        price = request.getPrice();
        discountRate = request.getDiscountRate();
        sellPrice = request.getSellPrice();
        stock = request.getStock();
        isSellCheck = request.isSellCheck();
        isDeleteCheck = request.isDeleteCheck();
        mainImg = request.getMainImg();
        serveImg = request.getServeImg();
    }


    // 재고 차감
    public void subStock(int itemCount){

        stock -= itemCount;
    }

    // 재고 증가
    public void plusStock(int itemCount){

        stock += itemCount;
    }


    public void delete(){
        category = null;
        name = null;
        content = null;
        isSellCheck = false;
        isDeleteCheck = true;
        mainImg = null;
        serveImg = null;
    }
}
