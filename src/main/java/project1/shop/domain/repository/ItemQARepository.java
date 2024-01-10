package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.ItemQA;
import project1.shop.domain.Level;

public interface ItemQARepository extends JpaRepository<ItemQA, Long> {
}
