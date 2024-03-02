package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.SearchDto;
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
    public WishDto.WishListPageResponse showWishList(@PathVariable Long id, SearchDto.Page request) {

        WishDto.WishListPageResponse wishListPageDto = wishService.showWishList(id, request);

        return wishListPageDto;
    }


    // 찜 추가
    @PostMapping("/member/{id}/wish")
    public void saveWishList(@PathVariable Long id, @RequestBody WishDto.WishRequest request){

        wishService.saveWishList(id, request);
    }

    // 찜 삭제
    @DeleteMapping("/member/{id}/wish")
    public void deleteWishList(@PathVariable Long id, @RequestParam("wishId") Long wishId){

        wishService.deleteWishList(id, wishId);
    }
}
