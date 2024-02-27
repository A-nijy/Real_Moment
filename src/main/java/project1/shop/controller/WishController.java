package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.WishDto;
import project1.shop.service.WishService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WishController {

    private final WishService wishService;


    // 찜 목록 조회
    @GetMapping("/member/{id}/wishList")
    public List<WishDto.WishListResponse> showWishList(@PathVariable Long id) {

        List<WishDto.WishListResponse> wishList = wishService.showWishList(id);

        return wishList;
    }


    // 찜 추가
    @PostMapping("/member/{id}/wish")
    public void saveWishList(@PathVariable Long id, @RequestParam("itemId") Long itemId){

        wishService.saveWishList(id, itemId);
    }

    // 찜 삭제
    @DeleteMapping("/member/{id}/wish")
    public void deleteWishList(@PathVariable Long id, @RequestParam("wishListId") Long wishListId){

        wishService.deleteWishList(id, wishListId);
    }
}
