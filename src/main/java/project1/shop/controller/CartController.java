package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.CartDto;
import project1.shop.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;



    // 장바구니 목록 조회
    @GetMapping("/carts/{id}")
    public List<CartDto.CartResponse> showCart(@PathVariable Long id){
        List<CartDto.CartResponse> cartsDto = cartService.showCart(id);

        return cartsDto;
    }


    // 장바구니 추가
    @PostMapping("/carts/{id}")
    public void saveCart(@PathVariable Long id, @RequestBody CartDto.CartSaveRequest request){

        cartService.saveCart(id, request);
    }


    // 장바구니 삭제
    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable Long id, @RequestParam("cartId") Long cartId){

        cartService.deleteCart(id, cartId);
    }


    // 장바구니 수정 (수량 수정)
    @PatchMapping("/carts/{id}/stock")
    public void updateStockCart(@PathVariable Long id, @RequestBody CartDto.CartUpdateStockRequest request){

        cartService.updateStockCart(id, request);
    }


    // 장바구니 수정 (체크 수정)
    @PatchMapping("/carts/{id}/check")
    public void updatedCheckCart(@PathVariable Long id, @RequestBody CartDto.CartUpdateCheckRequest request){

        cartService.updateCheckCart(id, request);
    }

}
