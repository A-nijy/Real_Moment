package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMember_MemberId(Long id);

    Optional<Cart> findByMember_MemberIdAndItem_ItemId(Long memberId, Long itemId);
}
