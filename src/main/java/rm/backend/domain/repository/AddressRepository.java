package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Address;
import rm.backend.domain.repository.custom.AddressRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {

    List<Address> findByMember_MemberId(Long id);

    Optional<Address> findByMember_MemberIdAndIsDefault(Long id, boolean isDefault);

}
