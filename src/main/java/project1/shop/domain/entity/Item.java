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
    private int sellCount = 0;
    private long revenue = 0;
    private boolean isSell;
    private boolean isDelete = false;



    public Item (Category category, ItemDto.SaveRequest request){
        this.category = category;
        name = request.getName();
        content = request.getContent();
        price = request.getPrice();
        discountRate = request.getDiscountRate();
        discountPrice = request.getDiscountPrice();
        sellPrice = request.getSellPrice();
        stock = request.getStock();
        isSell = request.isSell();
    }



    public void updateData(Category category, ItemDto.UpdateDataRequest request){
        this.category = category;
        name = request.getName();
        content = request.getContent();
        price = request.getPrice();
        discountRate = request.getDiscountRate();
        discountPrice = request.getDiscountPrice();
        sellPrice = request.getSellPrice();
        stock = request.getStock();
        isSell = request.isSell();
    }


    // 재고 차감
    public void subStock(int itemCount){

        stock -= itemCount;

        if(stock == 0){
            isSell = false;
        }
    }

    // 재고 증가
    public void plusStock(int itemCount){

        stock += itemCount;
    }

    // 판매량 추가
    public void plusSellCount(int sellCount){

        this.sellCount += sellCount;
    }

    // 판매량 복구
    public void subSellCount(int sellCount){

        this.sellCount -= sellCount;
    }

    // 매출 증가
    public void plusRevenue(int price){

        this.revenue += price;
    }

    // 매출 복구
    public void subRevenue(int price){

        this.revenue -= price;
    }


    public void delete(){
        category = null;
        name = null;
        content = null;
        isSell = false;
        isDelete = true;
    }
}
