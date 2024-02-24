package project1.shop.domain.repository.custom;

import project1.shop.domain.entity.Address;

public interface AddressRepositoryCustom {

    Address searchMainAddress(Long memberId);
}
