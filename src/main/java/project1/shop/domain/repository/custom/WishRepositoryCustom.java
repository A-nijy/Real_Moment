package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Wish;

public interface WishRepositoryCustom {

    Page<Wish> searchPageWishList(Long memberId, Pageable pageable);
}
