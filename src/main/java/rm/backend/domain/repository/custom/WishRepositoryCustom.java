package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Wish;

public interface WishRepositoryCustom {

    Page<Wish> searchPageWishList(Long memberId, Pageable pageable);
}
