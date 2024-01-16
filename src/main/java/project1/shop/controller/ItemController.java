package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.dto.ItemResponseDto;
import project1.shop.dto.ItemViewResponseDto;
import project1.shop.dto.ItemsResponseDto;
import project1.shop.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;


    // 카테고리 별 상품 목록 조회
    @GetMapping("/items")
    public List<ItemsResponseDto> showItems(@RequestParam("categoryId") Long id){

        log.info("컨트롤러 시작 -> 서비스 이동");
        List<ItemsResponseDto> items = itemService.showItems(id);

        log.info("서비스 종료 -> 반환");
        return items;
    }


    // 상세 상품 조회
    @GetMapping("/item")
    public ItemViewResponseDto showItem(@RequestParam("itemId") Long id){

        ItemViewResponseDto itemViewDto = itemService.showItem(id);

        return itemViewDto;
    }


}
