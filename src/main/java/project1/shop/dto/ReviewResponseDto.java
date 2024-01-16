package project1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.entity.Review;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewResponseDto {

    private Long id;
    private String title;
    private String content;
    private int star;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;



    public ReviewResponseDto(Review review){
        id = review.getReviewId();
        title = review.getTitle();
        content = review.getContent();
        star = review.getStar();
        writtenDate = review.getWrittenDate();
        editedDate = review.getEditedDate();
    }
}
