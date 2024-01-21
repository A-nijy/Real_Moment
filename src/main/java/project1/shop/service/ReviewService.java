package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Review;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.ReviewRepository;
import project1.shop.dto.innerDto.ReviewDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public List<ReviewDto.ReviewResponse> showReviews(Long itemId) {

        log.info("엔티티 리스트 생성");
        List<Review> reviews = reviewRepository.findByItem_ItemId(itemId);

        log.info("엔티티 -> dto 변환");
        List<ReviewDto.ReviewResponse> reviewsDto = reviews.stream()
                .map(ReviewDto.ReviewResponse::new)
                .collect(Collectors.toList());

        log.info("컨트롤러로 반환");
        return reviewsDto;
    }

    @Transactional
    public List<ReviewDto.MyReviewResponse> showMyReviews(Long memberId) {

        log.info("엔티티 리스트 생성");
        List<Review> reviews = reviewRepository.findByMember_MemberId(memberId);

        log.info("엔티티 -> dto 변환");
        List<ReviewDto.MyReviewResponse> reviewsDto = reviews.stream()
                .map(ReviewDto.MyReviewResponse::new)
                .collect(Collectors.toList());

        log.info("컨트롤러로 반환");
        return reviewsDto;
    }

    public void saveReview(Long id, ReviewDto.ReviewRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        Review review = new Review(member, item, request);

        reviewRepository.save(review);

    }

    public void updateReview(Long id, ReviewDto.ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);

        review.Update(request);

    }

    public void deleteReview(Long id, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        reviewRepository.delete(review);
    }
}
