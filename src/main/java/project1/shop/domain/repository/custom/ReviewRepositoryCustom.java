package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Review;
import project1.shop.dto.innerDto.SearchDto;

public interface ReviewRepositoryCustom {

    Page<Review> searchReviews(SearchDto.Reviews searchDto, Pageable pageable);

    Page<Review> searchMyReviews(Long MemberId, Pageable pageable);
}
