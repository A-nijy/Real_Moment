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


    public Cart(Member member, Item item, int stock){
        this.member = member;
        this.item = item;
        this.stock = stock;
    }


    public void updateStockCart(int stock){
        this.stock = stock;
    }

}
