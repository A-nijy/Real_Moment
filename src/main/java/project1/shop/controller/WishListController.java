package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.WishListDto;
import project1.shop.service.WishListService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WishListController {

    private final WishListService wishListService;


    // 찜 목록 조회
    @GetMapping("/wishList/{id}")
    public List<WishListDto.WishListResponse> showWishList(@PathVariable Long id) {

        List<WishListDto.WishListResponse> wishList = wishListService.showWishList(id);

        return wishList;
    }


    // 찜 추가
    @PostMapping("/wishList/{id}")
    public void saveWishList(@PathVariable Long id, @RequestParam("itemId") Long itemId){

        wishListService.saveWishList(id, itemId);
    }

    // 찜 삭제
    @DeleteMapping("/wishList/{id}")
    public void deleteWishList(@PathVariable Long id, @RequestParam("wishListId") Long wishListId){

        wishListService.deleteWishList(id, wishListId);
    }
}
