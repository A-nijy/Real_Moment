package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Address;
import project1.shop.dto.innerDto.SearchDto;

public interface AddressRepositoryCustom {

    Address searchMainAddress(Long memberId);

    Page<Address> searchMyAddressList(Long memberId, Pageable pageable);
}
