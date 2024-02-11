package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Item;
import project1.shop.domain.repository.custom.ItemRepositoryCustom;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> , ItemRepositoryCustom {


    List<Item> findByCategory_CategoryId(Long id);
}
