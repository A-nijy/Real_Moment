package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.ReviewDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;


    // 특정 상품의 리뷰 목록 조회
    @GetMapping("/reviews")
    public List<ReviewDto.ReviewResponse> showReviews(SearchDto.Reviews request){

        log.info("서비스 호출");
        List<ReviewDto.ReviewResponse> reviewsDto = reviewService.showReviews(request);

        return reviewsDto;
    }


    // 내가 작성한 리뷰 목록 조회
    @GetMapping("/reviews/{id}")
    public List<ReviewDto.MyReviewResponse> showMyReviews(@PathVariable Long id, SearchDto.Page nowPage){

        System.out.println("값 받아옴");
        List<ReviewDto.MyReviewResponse> reviewsDto = reviewService.showMyReviews(id, nowPage);

        return reviewsDto;
    }


    // 리뷰 추가
    @PostMapping("/review/{id}")
    public void saveReview(@PathVariable Long id, @RequestBody ReviewDto.ReviewRequest request){

        reviewService.saveReview(id, request);
    }


    // 리뷰 수정
    @PatchMapping("/review/{id}")
    public void updateReview(@PathVariable Long id, @RequestBody ReviewDto.ReviewUpdateRequest request){

        reviewService.updateReview(id, request);
    }


    // 리뷰 삭제
    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id, @RequestParam("reviewId") Long reviewId){

        log.info("서비스 호출");
        reviewService.deleteReview(id, reviewId);
    }
}
