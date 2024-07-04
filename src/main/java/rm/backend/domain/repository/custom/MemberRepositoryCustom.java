package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Grade;
import rm.backend.domain.entity.Member;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;

public interface MemberRepositoryCustom {

    Page<Member> searchMembers(SearchDto.Members searchDto, Pageable pageable);

    List<Member> findThisYearPayMember();


    List<Member> findBetweenThisYearPay(Grade grade, Grade highGrade);
}
