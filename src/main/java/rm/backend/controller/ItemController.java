package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.dto.innerDto.ItemDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.service.ItemService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;


    // (카테고리 or 검색 등) 상품 목록 조회
    @GetMapping("/itemList")
    public ItemDto.SimpleItemPageResponse showItems(SearchDto.Items request){

        ItemDto.SimpleItemPageResponse itemPageDto = itemService.showItems(request);

        return itemPageDto;
    }


    // 상품 상세 조회
    @GetMapping("/item")
    public ItemDto.FullItemResponse showItem(@RequestParam("itemId") Long itemId){

        ItemDto.FullItemResponse itemDto = itemService.showItem(itemId);

        return itemDto;
    }


}
