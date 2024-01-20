package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.WishList;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByMember_MemberId(Long id);

    Optional<WishList> findByWishListIdAndMember_MemberId(Long id, Long memberId);
}
