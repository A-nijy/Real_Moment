package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.ItemQA;
import project1.shop.dto.innerDto.SearchDto;

public interface ItemQARepositoryCustom {

    Page<ItemQA> searchAdminItemQAs(SearchDto.AdminItemQA adminItemQA, Pageable pageable);

    Page<ItemQA> searchItemInItemQAs(SearchDto.ItemInItemQA itemQA, Pageable pageable);

    Page<ItemQA> searchMyItemQAs(Long memberId, Pageable pageable);
}
