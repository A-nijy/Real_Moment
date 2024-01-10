package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private String title;
    private String content;
    private int star;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;
}
