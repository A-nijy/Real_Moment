package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
