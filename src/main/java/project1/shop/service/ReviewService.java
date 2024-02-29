package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Order;
import project1.shop.domain.entity.Review;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.OrderRepository;
import project1.shop.domain.repository.ReviewRepository;
import project1.shop.dto.innerDto.ReviewDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.enumeration.PaymentStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;


    // 내가 작성한 리뷰 목록 조회하기
    @Transactional
    public List<ReviewDto.MyReviewResponse> showMyReviews(Long memberId, SearchDto.Page nowPage) {


        PageRequest pageRequest = PageRequest.of(nowPage.getNowPage() - 1, 10);

        Page<Review> reviews = reviewRepository.searchMyReviews(memberId, pageRequest);

        log.info("엔티티 -> dto 변환");
        List<ReviewDto.MyReviewResponse> reviewsDto = reviews.stream()
                                                            .map(ReviewDto.MyReviewResponse::new)
                                                            .collect(Collectors.toList());

        log.info("컨트롤러로 반환");
        return reviewsDto;
    }


    // 리뷰 작성하기 (+ 주문 상태가 구매 확정인 경우에만 작성 가능)
    @Transactional
    public void saveReview(Long id, ReviewDto.ReviewRequest request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        if(!order.getStatus().equals(PaymentStatus.DONE)){
            throw new IllegalArgumentException("주문상태가 구매 확정만 리뷰를 작성할 수 있습니다.");
        }

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        Review review = new Review(member, item, request);

        reviewRepository.save(review);

    }


    // 리뷰 수정 버튼 클릭시 해당 리뷰 데이터 반환
    @Transactional
    public ReviewDto.ReviewUpdateResponse getReview(Long id, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        ReviewDto.ReviewUpdateResponse reviewDto = new ReviewDto.ReviewUpdateResponse(review);

        return reviewDto;
    }


    // 리뷰 수정하기
    @Transactional
    public void updateReview(Long id, ReviewDto.ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(request.getReviewId()).orElseThrow(IllegalArgumentException::new);

        review.Update(request);

    }

    // 리뷰 삭제하기
    @Transactional
    public void deleteReview(Long id, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        reviewRepository.delete(review);
    }


}
