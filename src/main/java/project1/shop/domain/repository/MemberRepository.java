package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
