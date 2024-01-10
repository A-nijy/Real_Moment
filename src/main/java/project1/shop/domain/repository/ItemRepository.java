package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Item;
import project1.shop.domain.Level;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
