package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.OneOnOne;
import rm.backend.dto.innerDto.SearchDto;

public interface OneOnOneRepositoryCustom {

    Page<OneOnOne> searchMyOneOnOne(SearchDto.OneOnOnes searchDto, Long memberId, Pageable pageable);

    Page<OneOnOne> searchOneOnOne(SearchDto.OneOnOneSearch searchDto, Pageable pageable);
}
