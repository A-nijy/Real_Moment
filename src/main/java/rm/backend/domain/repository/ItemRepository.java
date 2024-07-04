package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Item;
import rm.backend.domain.repository.custom.ItemRepositoryCustom;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> , ItemRepositoryCustom {


    List<Item> findByCategory_CategoryId(Long id);
}
