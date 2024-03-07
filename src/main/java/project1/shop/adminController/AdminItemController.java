package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminItemService;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.SearchDto;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminItemController {

    private final AdminItemService adminItemService;


    // (카테고리 or 검색 등) 상품 목록 조회
    @GetMapping("/admin/itemList")
    public ItemDto.SimpleItemPageResponse showItems(SearchDto.Items request){

        ItemDto.SimpleItemPageResponse itemPageDto = adminItemService.showItems(request);

        return itemPageDto;
    }


    // 상품 상세 조회
    @GetMapping("/admin/item")
    public ItemDto.FullItemResponse showItem(@RequestParam("itemId") Long itemId){

        ItemDto.FullItemResponse itemDto = adminItemService.showItem(itemId);

        return itemDto;
    }


    // 상품 추가
    @PostMapping("/admin/item")
    public String saveItem(ItemDto.SaveRequest request) throws IOException {

        adminItemService.saveItem(request);

        return "상품 등록 완료!";
    }


    // 상품 수정 버튼 클릭시 해당 데이터 가져옴
    @GetMapping("/admin/item/data")
    public ItemDto.UpdateResponse getItem(@RequestParam("itemId") Long itemId){

        ItemDto.UpdateResponse itemDto = adminItemService.getItem(itemId);

        return itemDto;
    }


    // 상품 수정 (상품 정보)
    @PatchMapping("/admin/item/data")
    public String updateItemData(@RequestBody ItemDto.UpdateDataRequest request){

        adminItemService.updateItemData(request);

        return "상품 정보 수정 완료";
    }


    // 상품 수정 (메인 이미지)
    @PatchMapping("/admin/item/mainImg")
    public String updateItemMainImg(@RequestBody ItemDto.UpdateMainImgRequest request){

        adminItemService.updateItemMainImg(request);

        return "메인 이미지 수정 완료";
    }


    // 상품 수정 (서브 이미지)
    @PatchMapping("/admin/item/serveImg")
    public String updateItemServeImg(@RequestBody ItemDto.UpdateServeImgRequest request) {

        adminItemService.updateItemServeImg(request);

        return "서브 이미지 수정 완료";
    }


    // 상품 삭제
    @DeleteMapping("/admin/item")
    public String deleteItem(@RequestParam("itemId") Long itemId){

        adminItemService.deleteItem(itemId);

        return "상품 삭제 완료!";
    }
}
