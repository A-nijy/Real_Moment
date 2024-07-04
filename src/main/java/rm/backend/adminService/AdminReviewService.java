package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Review;
import rm.backend.domain.repository.ReviewRepository;
import rm.backend.dto.innerDto.ReviewDto;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReviewService {

    private final ReviewRepository reviewRepository;


    // 상품 리뷰 목록 조회
    @Transactional
    public ReviewDto.AllReviewResponse showReviews(SearchDto.Reviews request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Review> reviews = reviewRepository.searchReviews(request, pageRequest);

        List<ReviewDto.ReviewResponse> reviewsDto = reviews.stream()
                .map(ReviewDto.ReviewResponse::new)
                .collect(Collectors.toList());

        ReviewDto.AllReviewResponse reviewPageDto = new ReviewDto.AllReviewResponse(reviewsDto, reviews.getTotalPages(), request.getNowPage());

        return reviewPageDto;
    }
}
