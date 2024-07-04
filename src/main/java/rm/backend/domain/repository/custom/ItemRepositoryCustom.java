package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Item;
import rm.backend.dto.innerDto.SearchDto;

public interface ItemRepositoryCustom {

    Page<Item> searchPageSimple(SearchDto.Items searchDto, Pageable pageable);

    Page<Item> searchAdminPageSimple(SearchDto.AdminItems searchDto, Pageable pageable);
}
