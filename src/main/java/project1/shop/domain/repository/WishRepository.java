package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Wish;
import project1.shop.domain.repository.custom.WishRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom {

    List<Wish> findByMember_MemberId(Long id);

    Optional<Wish> findByWishIdAndMember_MemberId(Long id, Long memberId);

    Optional<Wish> findByMember_memberIdAndItem_ItemId(Long memberId, Long itemId);
}
