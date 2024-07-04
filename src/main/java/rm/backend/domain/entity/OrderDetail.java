package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int price;                      // 결제 당시 정가
    private int discountRate;               // 결제 당시 할인율
    private int discountPrice;              // 결제 당시 할인가
    private int sellPrice;                  // 결제 당시 판매가
    private int itemCount;                  // 결제 당시 구매 개수
    private int totalPrice;                 // 결제 당시 총 구매가

    private boolean isReviewStatus = false;           // 리뷰작성 여부


    public OrderDetail(Order order, Item item, int itemCount){

        this.order = order;
        this.item = item;
        price = item.getPrice();
        discountRate = item.getDiscountRate();
        discountPrice = item.getDiscountPrice();
        sellPrice = item.getSellPrice();
        this.itemCount = itemCount;
        totalPrice = sellPrice * itemCount;
    }

    public void writeReview(){
        isReviewStatus = true;
    }

    public void deleteReview() {
        isReviewStatus = false;
    }
}
