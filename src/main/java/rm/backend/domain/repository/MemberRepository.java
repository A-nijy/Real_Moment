package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Member;
import rm.backend.domain.repository.custom.MemberRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByLoginId(String loginId);

    List<Member> findByGrade_GradeId(Long gradeId);

    List<Member> findByThisYearPayGreaterThanEqual(Integer gradePrice);


//    Optional<Member> findByNickname(String nickname);
}
