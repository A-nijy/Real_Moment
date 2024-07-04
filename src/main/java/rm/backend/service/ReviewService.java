package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.*;
import rm.backend.domain.repository.*;
import rm.backend.dto.innerDto.ItemDto;
import rm.backend.dto.innerDto.ReviewDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.enumeration.PaymentStatus;

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
    private final OrderDetailRepository orderDetailRepository;
    private final ItemFileRepository itemFileRepository;



    // 특정 상품에 대한 리뷰 목록 조회
    @Transactional
    public ReviewDto.ReviewPageResponse showItemReviews(SearchDto.Reviews request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        Page<Review> reviews = reviewRepository.searchReviews(request, pageRequest);

        List<ReviewDto.ReviewResponse> reviewsDto = reviews.stream()
                .map(ReviewDto.ReviewResponse::new)
                .collect(Collectors.toList());

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ReviewDto.ReviewPageResponse reviewPageDto = new ReviewDto.ReviewPageResponse(reviewsDto, item, reviews.getTotalPages(), request.getNowPage());

        return reviewPageDto;
    }



    // 내가 작성한 리뷰 목록 조회하기
    @Transactional
    public ReviewDto.MyReviewPageResponse showMyReviews(Long memberId, SearchDto.Page request) {


        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Review> reviews = reviewRepository.searchMyReviews(memberId, pageRequest);

        List<Review> reviewList = reviewRepository.findByMember_MemberId(memberId);

        log.info("엔티티 -> dto 변환");
        List<ReviewDto.MyReviewResponse> reviewsDto = reviews.stream()
                                                            .map(this::mapToDto)
                                                            .collect(Collectors.toList());

        ReviewDto.MyReviewPageResponse reviewPageDto = new ReviewDto.MyReviewPageResponse(reviewsDto,reviewList.size(), reviews.getTotalPages(), request.getNowPage());

        log.info("컨트롤러로 반환");
        return reviewPageDto;
    }


    // DTO 변환
    public ReviewDto.MyReviewResponse mapToDto (Review review){

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(review.getItem()).orElse(null);

        ItemDto.SimpleItemResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemResponse(review.getItem(), null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemResponse(review.getItem(), itemFile.getS3File().getFileUrl());
        }

        ReviewDto.MyReviewResponse reviewDto = new ReviewDto.MyReviewResponse(review, simpleItemDto);

        return reviewDto;
    }


    // 리뷰 작성하기 (+ 주문 상태가 구매 확정인 경우에만 작성 가능)
    @Transactional
    public void saveReview(Long id, ReviewDto.ReviewRequest request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        if(!order.getStatus().equals(PaymentStatus.DONE)){
            throw new IllegalArgumentException("주문상태가 구매 확정만 리뷰를 작성할 수 있습니다.");
        }

        // 해당 상품을 주문한 적이 있는지 확인
        OrderDetail orderDetail = orderDetailRepository.findByOrder_OrderIdAndItem_ItemId(request.getOrderId(), request.getItemId()).orElseThrow(IllegalArgumentException::new);


        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        Review review = new Review(member, item, request);

        orderDetail.writeReview();

        reviewRepository.save(review);

        item.saveStar(request.getStar());
        review.getItem().aveStarCalculate();
    }


    // 리뷰 수정 버튼 클릭시 해당 리뷰 데이터 반환
    @Transactional
    public ReviewDto.ReviewUpdateResponse getReview(Long id, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        if(id != review.getMember().getMemberId()){
            throw new IllegalArgumentException("다른 회원의 리뷰입니다.");
        }

        ReviewDto.ReviewUpdateResponse reviewDto = new ReviewDto.ReviewUpdateResponse(review);

        return reviewDto;
    }


    // 리뷰 수정하기
    @Transactional
    public void updateReview(Long id, ReviewDto.ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(request.getReviewId()).orElseThrow(IllegalArgumentException::new);

        if(id != review.getMember().getMemberId()){
            throw new IllegalArgumentException("다른 회원의 리뷰입니다.");
        }

        review.getItem().deleteStar(review.getStar());
        review.getItem().saveStar(request.getStar());
        review.getItem().aveStarCalculate();

        review.Update(request);

    }

    // 리뷰 삭제하기
    @Transactional
    public void deleteReview(Long id, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        if(id != review.getMember().getMemberId()){
            throw new IllegalArgumentException("다른 회원의 리뷰입니다.");
        }

        review.getItem().deleteStar(review.getStar());
        review.getItem().aveStarCalculate();

        reviewRepository.delete(review);
    }



}
