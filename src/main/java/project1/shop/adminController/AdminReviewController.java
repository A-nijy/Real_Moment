package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.adminService.AdminReviewService;
import project1.shop.dto.innerDto.ReviewDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;


    // 상품의 리뷰 목록 조회
    @GetMapping("/admin/reviewList/view")
    public ReviewDto.AllReviewResponse showReviews(SearchDto.Reviews request){

        log.info("서비스 호출");
        ReviewDto.AllReviewResponse reviewPageDto = adminReviewService.showReviews(request);

        return reviewPageDto;
    }
}
