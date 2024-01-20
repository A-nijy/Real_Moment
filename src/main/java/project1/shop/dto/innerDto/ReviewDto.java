package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Review;

import java.time.LocalDateTime;

public class ReviewDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReviewResponse {
        private Long id;
        private String title;
        private String content;
        private int star;
        private LocalDateTime writtenDate;
        private LocalDateTime editedDate;



        public ReviewResponse(Review review){
            id = review.getReviewId();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
            writtenDate = review.getWrittenDate();
            editedDate = review.getEditedDate();
        }
    }
}
