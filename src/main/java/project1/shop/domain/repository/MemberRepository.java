package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Order;
import project1.shop.domain.repository.custom.MemberRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByLoginId(String loginId);

    List<Member> findByGrade_GradeId(Long gradeId);

    List<Member> findByThisYearPayGreaterThanEqual(Integer gradePrice);


//    Optional<Member> findByNickname(String nickname);
}
