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
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReviewUpdateRequest{
        private Long reviewId;
        private String title;
        private String content;
        private int star;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReviewResponse {
        private Long reviewId;
//        private String nickname;
        private String loginId;
        private String title;
        private String content;
        private int star;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;



        public ReviewResponse(Review review){
            reviewId = review.getReviewId();
//            nickname = review.getMember().getNickname();
            loginId = review.getMember().getLoginId();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
            createdDate = review.getCreatedDate();
            lastModifiedDate = review.getLastModifiedDate();
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyReviewResponse {
        private Long reviewId;
        private ItemDto.SimpleItemResponse item;
//        private String nickname;
        private String loginId;
        private String title;
        private String content;
        private int star;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;


        public MyReviewResponse(Review review){
            reviewId = review.getReviewId();
            item = new ItemDto.SimpleItemResponse(review.getItem());
//            nickname = review.getMember().getNickname();
            loginId = review.getMember().getLoginId();
            title = review.getTitle();
            content = review.getContent();
            star = review.getStar();
            createdDate = review.getCreatedDate();
            lastModifiedDate = review.getLastModifiedDate();
        }
    }
}
