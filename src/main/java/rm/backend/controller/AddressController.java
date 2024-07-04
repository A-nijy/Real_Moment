package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.dto.innerDto.AddressDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.service.AddressService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;


    // 기본 설정 배송지 조회 (주문 페이지로 이동할 때 미리 세팅하기 위해 가져오기 위한 api)
    @GetMapping("/member/{id}/address")
    public AddressDto.AddressResponse getDefaultAddress(@PathVariable Long id){

        AddressDto.AddressResponse addressDto = addressService.getDefaultAddress(id);

        return addressDto;
    }


    // 배송지(주소) 목록 조회
    @GetMapping("/member/{id}/addressList")
    public AddressDto.AddressPageResponse showAddresses(@PathVariable Long id, SearchDto.Page request) {

        AddressDto.AddressPageResponse addressPageDto = addressService.showAddresses(id, request);

        return addressPageDto;
    }

    // 배송지(주소) 추가
    @PostMapping("/member/{id}/address")
    public void saveAddress(@PathVariable Long id, @RequestBody AddressDto.SaveRequest request) {

        addressService.saveAddress(id, request);

        log.info("배송지 저장 완료");
    }

    // 배송지(주소) 수정
    @PatchMapping("/member/{id}/address")
    public void updateAddress(@PathVariable Long id, @RequestBody AddressDto.UpdateRequest request) {

        addressService.updateAddress(id, request);
    }

    // 배송지(주소) 삭제
    @DeleteMapping("/member/{id}/address")
    public void deleteAddress(@PathVariable Long id, @RequestParam("addressId") Long addressId) {

        addressService.deleteAddress(id, addressId);
    }


}
