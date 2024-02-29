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





    // 내가 작성한 리뷰 목록 조회
    @GetMapping("/member/{id}/reviewList")
    public List<ReviewDto.MyReviewResponse> showMyReviews(@PathVariable Long id, SearchDto.Page nowPage){

        System.out.println("값 받아옴");
        List<ReviewDto.MyReviewResponse> reviewsDto = reviewService.showMyReviews(id, nowPage);

        return reviewsDto;
    }


    // 리뷰 추가
    @PostMapping("/member/{id}/review")
    public void saveReview(@PathVariable Long id, @RequestBody ReviewDto.ReviewRequest request){

        reviewService.saveReview(id, request);
    }


    // 리뷰 수정 버튼 (해당 리뷰 데이터 가져옴)
    @GetMapping("/member/{id}/review")
    public ReviewDto.ReviewUpdateResponse getReview(@PathVariable Long id, @RequestParam("reviewId") Long reviewId){

        ReviewDto.ReviewUpdateResponse reviewDto = reviewService.getReview(id, reviewId);

        return reviewDto;
    }



    // 리뷰 수정
    @PatchMapping("/member/{id}/review")
    public void updateReview(@PathVariable Long id, @RequestBody ReviewDto.ReviewUpdateRequest request){

        reviewService.updateReview(id, request);
    }


    // 리뷰 삭제
    @DeleteMapping("/member/{id}/review")
    public void deleteReview(@PathVariable Long id, @RequestParam("reviewId") Long reviewId){

        log.info("서비스 호출");
        reviewService.deleteReview(id, reviewId);
    }
}
