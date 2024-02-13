package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.custom.MemberRepositoryCustom;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByLoginId(String loginId);

//    Optional<Member> findByNickname(String nickname);
}
