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
    public static class ReviewRequest{
        private Long itemId;
        private String title;
        private String content;
        private int star;
        private LocalDateTime writtenDate = null;
        private LocalDateTime editedDate = null;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReviewUpdateRequest{
        private Long id;
        private String title;
        private String content;
        private int star;
        private LocalDateTime writtenDate = null;
        private LocalDateTime editedDate = null;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReviewResponse {
        private Long id;
        private String nickname;
        private String title;
        private String content;
        private int star;
        private LocalDateTime writtenDate;
        private LocalDateTime editedDate;



        public ReviewResponse(Review review){
            id = review.getReviewId();
            nickname = review.getMember().getNickname();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
            writtenDate = review.getWrittenDate();
            editedDate = review.getEditedDate();
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyReviewResponse {
        private Long id;
        private ItemDto.SimpleItemResponse item;
        private String nickname;
        private String title;
        private String content;
        private int star;
        private LocalDateTime writtenDate;
        private LocalDateTime editedDate;


        public MyReviewResponse(Review review){
            id = review.getReviewId();
            item = new ItemDto.SimpleItemResponse(review.getItem());
            nickname = review.getMember().getNickname();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
            writtenDate = review.getWrittenDate();
            editedDate = review.getEditedDate();
        }
    }
}
