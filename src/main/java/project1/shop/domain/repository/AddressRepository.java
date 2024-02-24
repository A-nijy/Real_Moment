package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Address;
import project1.shop.domain.repository.custom.AddressRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {

    List<Address> findByMember_MemberId(Long id);

}
