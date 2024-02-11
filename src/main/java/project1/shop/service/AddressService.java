package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Address;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.AddressRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.AddressDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public List<AddressDto.AddressResponse> showAddresses(Long id) {

        log.info("id = {}", id);
        log.info("id로 데이터 가져오기");
        List<Address> addresses = addressRepository.findByMember_MemberId(id);

        log.info("엔티티 리스트 = {}", addresses);
        log.info("엔티티 -> dto 변환");
        List<AddressDto.AddressResponse> addressesDto = addresses.stream()
                .map(AddressDto.AddressResponse::new)
                .collect(Collectors.toList());

        log.info("dto 리스트 = {}", addressesDto);
        log.info("dto 리스트 다시 컨트롤러에 반환");

        return addressesDto;
    }

    @Transactional
    public void saveAddress(Long id, AddressDto.SaveRequest requestDto) {

        log.info("서비스 호출 -> member 엔티티 조회");
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        log.info("Address 엔티티 생성과 동시에 저장");
        addressRepository.save(new Address(requestDto, member));
    }

    @Transactional
    public void updateAddress(Long id, AddressDto.UpdateRequest request) {

        Address address = addressRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);

        address.update(request);
    }

    @Transactional
    public void deleteAddress(Long id, Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(IllegalArgumentException::new);

        addressRepository.delete(address);
    }
}
