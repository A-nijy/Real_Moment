package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.ItemQA;
import project1.shop.dto.innerDto.SearchDto;

public interface ItemQARepositoryCustom {

    Page<ItemQA> searchItemQAs(SearchDto.ItemQAs itemQAs, Pageable pageable);

    Page<ItemQA> searchMyItemQAs(Long memberId, Pageable pageable);
}
