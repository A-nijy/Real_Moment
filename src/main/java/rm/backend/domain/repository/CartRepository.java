package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Cart;
import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMember_MemberIdOrderByCartIdDesc(Long id);

    Optional<Cart> findByMember_MemberIdAndItem_ItemId(Long memberId, Long itemId);

    Optional<Cart> findByMemberAndItem(Member member, Item item);
}
