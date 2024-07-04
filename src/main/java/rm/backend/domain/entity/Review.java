package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.domain.AutoCheck.TimeCheck;
import rm.backend.dto.innerDto.ReviewDto;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review extends TimeCheck {

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


    public Review(Member member, Item item, ReviewDto.ReviewRequest request){
        this.member = member;
        this.item = item;
        title = request.getTitle();
        content = request.getContent();
        star = request.getStar();
    }

    public void Update(ReviewDto.ReviewUpdateRequest request){
        title = request.getTitle();
        content = request.getContent();
        star = request.getStar();
    }
}
