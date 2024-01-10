package project1.shop.domain;

import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int fixedPrice;
    private int discountRate;
    private int sellPrice;
    private int itemCount;
}
