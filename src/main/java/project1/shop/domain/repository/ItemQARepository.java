package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Item;
import project1.shop.domain.ItemQA;

import java.util.List;

public interface ItemQARepository extends JpaRepository<ItemQA, Long> {

    List<ItemQA> findByItem_ItemId(Long id);
}
