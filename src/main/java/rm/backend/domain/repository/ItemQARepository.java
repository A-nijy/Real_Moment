package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.ItemQA;
import rm.backend.domain.repository.custom.ItemQARepositoryCustom;

import java.util.List;

public interface ItemQARepository extends JpaRepository<ItemQA, Long>, ItemQARepositoryCustom {

    List<ItemQA> findByItem_ItemId(Long id);
    List<ItemQA> findByMember_MemberId(Long id);
}
