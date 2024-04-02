package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Item;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

public interface ItemRepositoryCustom {

    Page<Item> searchPageSimple(SearchDto.Items searchDto, Pageable pageable);

    Page<Item> searchAdminPageSimple(SearchDto.AdminItems searchDto, Pageable pageable);
}
