package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Address;

public interface AddressRepositoryCustom {

    Address searchMainAddress(Long memberId);

    Page<Address> searchMyAddressList(Long memberId, Pageable pageable);
}
