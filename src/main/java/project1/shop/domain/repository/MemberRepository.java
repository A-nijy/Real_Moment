package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
