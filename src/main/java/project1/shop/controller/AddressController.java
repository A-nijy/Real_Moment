package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.AddressRequestDto;
import project1.shop.dto.AddressResponseDto;
import project1.shop.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;


    // 배송지(주소) 목록 조회
    @GetMapping("/member/{id}/addresses")
    public List<AddressResponseDto> showAddresses(@PathVariable Long id) {

        log.info("id = {}", id);
        log.info("컨트롤러 수행 -> 서비스 호출");
        List<AddressResponseDto> addressesDto = addressService.showAddresses(id);

        log.info("데이터 받아옴");

        return addressesDto;
    }

    // 배송지(주소) 추가
    @PostMapping("/member/{id}/address")
    public void saveAddress(@PathVariable Long id, @RequestBody AddressRequestDto requestDto) {

        log.info("컨트롤러 수행 -> 서비스 호출");
        addressService.saveAddress(id, requestDto);

        log.info("배송지 저장 완료");
    }

    // 배송지(주소) 수정
    @PatchMapping("/member/{id}/address")
    public void updateAddress(@PathVariable Long id, @RequestBody AddressRequestDto requestDto) {

        addressService.updateAddress(id, requestDto);
    }

    // 배송지(주소) 삭제
    @DeleteMapping("/member/{id}/address")
    public void deleteAddress(@PathVariable Long id, @RequestBody AddressRequestDto requestDto) {

        addressService.deleteAddress(id, requestDto);
    }


}
