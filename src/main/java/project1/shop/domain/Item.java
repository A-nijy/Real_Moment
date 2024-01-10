package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Item {

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
    private int sellPrice;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int stock;
    private boolean isSellCheck;
    private boolean isDeleteCheck;
    private String mainImg;
    private String serveImg;
}
