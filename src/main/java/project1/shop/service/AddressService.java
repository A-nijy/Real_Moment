package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Address;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.AddressRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.AddressDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;


    // 기본 설정 배송지 조회 (주문 페이지로 이동할 때 미리 세팅하기 위해 가져오기 위한 api)
    @Transactional
    public AddressDto.AddressResponse getDefaultAddress(Long id) {

        Address address = addressRepository.searchMainAddress(id);
        AddressDto.AddressResponse addressDto = null;
        if (!(address == null)){
            addressDto = new AddressDto.AddressResponse(address);
        }

        return addressDto;
    }



    // 배송지 목록 조회
    @Transactional
    public AddressDto.AddressPageResponse showAddresses(Long id, SearchDto.Page request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        Page<Address> addressList = addressRepository.searchMyAddressList(id, request, pageRequest);

        List<AddressDto.AddressResponse> addressesDto = addressList.stream()
                .map(AddressDto.AddressResponse::new)
                .collect(Collectors.toList());

        AddressDto.AddressPageResponse addressPageDto = new AddressDto.AddressPageResponse(addressesDto, addressList.getTotalPages(), request.getNowPage());

        return addressPageDto;
    }


    // 배송지 저장
    @Transactional
    public void saveAddress(Long id, AddressDto.SaveRequest requestDto) {

        log.info("서비스 호출 -> member 엔티티 조회");
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        log.info("Address 엔티티 생성과 동시에 저장");
        addressRepository.save(new Address(requestDto, member));
    }


    // 배송지 수정
    @Transactional
    public void updateAddress(Long id, AddressDto.UpdateRequest request) {

        Address address = addressRepository.findById(request.getAddressId()).orElseThrow(IllegalArgumentException::new);

        address.update(request);
    }


    // 배송지 삭제
    @Transactional
    public void deleteAddress(Long id, Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(IllegalArgumentException::new);

        addressRepository.delete(address);
    }



}
