package project1.shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemQA {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_QA_id")
    private Long itemQAId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private String title;
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;
}
