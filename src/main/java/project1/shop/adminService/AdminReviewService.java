package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Review;
import project1.shop.domain.repository.ReviewRepository;
import project1.shop.dto.innerDto.ReviewDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReviewService {

    private final ReviewRepository reviewRepository;


    // 상품 리뷰 목록 조회
    @Transactional
    public ReviewDto.ReviewPageResponse showReviews(SearchDto.Reviews request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Review> reviews = reviewRepository.searchReviews(request, pageRequest);

        List<ReviewDto.ReviewResponse> reviewsDto = reviews.stream()
                .map(ReviewDto.ReviewResponse::new)
                .collect(Collectors.toList());

        ReviewDto.ReviewPageResponse reviewPageDto = new ReviewDto.ReviewPageResponse(reviewsDto, reviews.getTotalPages(), request.getNowPage());

        return reviewPageDto;
    }
}
