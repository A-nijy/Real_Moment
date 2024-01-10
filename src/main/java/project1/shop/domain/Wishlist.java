package project1.shop.domain;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "whishlist_id")
    private Long whishlistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
