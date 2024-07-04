package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Review;
import rm.backend.dto.innerDto.SearchDto;

public interface ReviewRepositoryCustom {

    Page<Review> searchReviews(SearchDto.Reviews searchDto, Pageable pageable);

    Page<Review> searchMyReviews(Long MemberId, Pageable pageable);
}
