package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminItemService;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminItemController {

    private final AdminItemService adminItemService;


    // (카테고리 or 검색 등) 상품 목록 조회
    @GetMapping("/admin/itemList")
    public List<ItemDto.SimpleItemResponse> showItems(SearchDto.Items request){

        List<ItemDto.SimpleItemResponse> itemsDto = adminItemService.showItems(request);

        return itemsDto;
    }


    // 상품 상세 조회
    @GetMapping("/admin/item")
    public ItemDto.FullItemResponse showItem(@RequestParam("itemId") Long itemId){

        ItemDto.FullItemResponse itemDto = adminItemService.showItem(itemId);

        return itemDto;
    }


    // 상품 추가
    @PostMapping("/admin/item")
    public String saveItem(@RequestBody ItemDto.SaveRequest request){

        adminItemService.saveItem(request);

        return "상품 등록 완료!";
    }


    // 상품 수정 버튼 클릭시 해당 데이터 가져옴
    @GetMapping("/admin/item")
    public ItemDto.UpdateResponse getItem(@RequestParam("itemId") Long itemId){

        ItemDto.UpdateResponse itemDto = adminItemService.getItem(itemId);

        return itemDto;
    }


    // 상품 수정
    @PatchMapping("/admin/item")
    public String updateItem(@RequestBody ItemDto.UpdateRequest request){

        adminItemService.updateItem(request);

        return "상품 수정 완료!";
    }


    // 상품 삭제
    @DeleteMapping("/admin/item")
    public String deleteItem(@RequestParam("itemId") Long itemId){

        adminItemService.deleteItem(itemId);

        return "상품 삭제 완료!";
    }
}
