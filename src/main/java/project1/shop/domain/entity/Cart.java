package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project1.shop.dto.innerDto.CartDto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int stock;
    private int price;
    private boolean isCheck = true;


    public Cart(Member member, Item item, int stock){
        this.member = member;
        this.item = item;
        this.stock = stock;
        price = item.getPrice()*((100 - item.getDiscountRate()) / 100) * stock;
    }


    public void updateStockCart(int stock){
        this.stock = stock;
        log.info("getPrice = {}, getDiscountRate = {}, this.stock = {}, stock = {}, price = {}", item.getPrice(), item.getDiscountRate(), this.stock, stock, price);

        double discount = (double)(100 - item.getDiscountRate()) / 100;
        log.info("price = {}", discount);


        price = (int)(item.getPrice() * discount * this.stock);
        log.info("price = {}", price);
    }

    public void updateCheckCart(boolean isCheck){
        this.isCheck = isCheck;
    }
}
