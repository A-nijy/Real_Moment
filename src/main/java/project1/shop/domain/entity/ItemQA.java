package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeCheck;
import project1.shop.dto.innerDto.ItemQADto;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemQA extends TimeCheck {

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
    private boolean answer = false;


    public ItemQA(Member member, Item item, ItemQADto.ItemQARequest request){
        this.member = member;
        this.item = item;
        title = request.getTitle();
        content = request.getContent();;
    }


    public void update(ItemQADto.UpdateItemQARequest request){

        title = request.getTitle();
        content = request.getContent();
    }
}
