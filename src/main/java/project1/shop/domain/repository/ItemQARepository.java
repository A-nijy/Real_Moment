package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.repository.custom.ItemQARepositoryCustom;

import java.util.List;

public interface ItemQARepository extends JpaRepository<ItemQA, Long>, ItemQARepositoryCustom {

    List<ItemQA> findByItem_ItemId(Long id);
    List<ItemQA> findByMember_MemberId(Long id);
}
