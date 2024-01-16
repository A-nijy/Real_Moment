package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.ItemQA;

import java.util.List;

public interface ItemQARepository extends JpaRepository<ItemQA, Long> {

    List<ItemQA> findByItem_ItemId(Long id);
}
