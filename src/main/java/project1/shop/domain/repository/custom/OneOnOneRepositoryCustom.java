package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.OneOnOne;
import project1.shop.dto.innerDto.SearchDto;

public interface OneOnOneRepositoryCustom {

    Page<OneOnOne> searchMyOneOnOne(SearchDto.OneOnOnes searchDto, Long memberId, Pageable pageable);

    Page<OneOnOne> searchOneOnOne(SearchDto.OneOnOneSearch searchDto, Pageable pageable);
}
