package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.domain.AutoCheck.TimeAndByCheck;
import rm.backend.dto.innerDto.ItemDto;

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

    private int fiveStar = 0;
    private int fourStar = 0;
    private int threeStar = 0;
    private int twoStar = 0;
    private int oneStar = 0;
    private double averageStar = 0.0;

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

    // 리뷰 별점 저장 (리뷰 추가 및 수정)
    public void saveStar(int star){

        if (star == 1){
            oneStar++;
        } else if(star == 2){
            twoStar++;
        } else if(star == 3){
            threeStar++;
        } else if(star == 4){
            fourStar++;
        } else if(star == 5){
            fiveStar++;
        } else {
            throw new IllegalArgumentException("올바른 평점이 아닙니다.");
        }
    }

    // 리뷰 별점 삭제 (리뷰 삭제 및 수정)
    public void deleteStar(int star){

        if (star == 1){
            oneStar--;
        } else if(star == 2){
            twoStar--;
        } else if(star == 3){
            threeStar--;
        } else if(star == 4){
            fourStar--;
        } else if(star == 5){
            fiveStar--;
        } else {
            throw new IllegalArgumentException("올바른 평점이 아닙니다.");
        }
    }

    // 리뷰 평점 계산
    public void aveStarCalculate(){
        averageStar = (double)((fiveStar * 5) + (fourStar * 4) + (threeStar * 3) + (twoStar * 2) + oneStar) / (fiveStar + fourStar + threeStar + twoStar + oneStar);
    }


    public void delete(){
        category = null;
        name = null;
        content = null;
        isSell = false;
        isDelete = true;
    }
}
