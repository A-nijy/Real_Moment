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
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;



    // 장바구니 목록 조회
    @GetMapping("/member/{id}/cartList")
    public List<CartDto.CartResponse> showCart(@PathVariable Long id){
        List<CartDto.CartResponse> cartsDto = cartService.showCart(id);

        return cartsDto;
    }


    // 장바구니 추가
    @PostMapping("/member/{id}/cart")
    public void saveCart(@PathVariable Long id, @RequestBody CartDto.CartSaveRequest request){

        cartService.saveCart(id, request);
    }


    // 장바구니 삭제
    @DeleteMapping("/member/{id}/cart")
    public void deleteCart(@PathVariable Long id, @RequestParam("cartId") Long cartId){

        cartService.deleteCart(id, cartId);
    }


    // 장바구니 수정 (수량 수정)
    @PatchMapping("/member/{id}/cart/count")
    public void updateStockCart(@PathVariable Long id, @RequestBody CartDto.CartUpdateCountRequest request){

        cartService.updateStockCart(id, request);
    }

}
