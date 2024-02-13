package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Member;
import project1.shop.dto.innerDto.SearchDto;

public interface MemberRepositoryCustom {

    Page<Member> searchMembers(SearchDto.Members searchDto, Pageable pageable);
}
