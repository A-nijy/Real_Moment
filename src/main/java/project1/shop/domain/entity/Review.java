package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.dto.innerDto.ReviewDto;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
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


    public Review(Member member, Item item, ReviewDto.ReviewRequest request){
        this.member = member;
        this.item = item;
        title = request.getTitle();
        content = request.getContent();
        star = request.getStar();
        writtenDate = request.getWrittenDate();
        editedDate = request.getEditedDate();
    }

    public void Update(ReviewDto.ReviewUpdateRequest request){
        title = request.getTitle();
        content = request.getContent();
        star = request.getStar();
        writtenDate = request.getWrittenDate();
        editedDate = request.getEditedDate();
    }
}
