package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminItemService;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.SearchDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminItemController {

    private final AdminItemService adminItemService;


    // (카테고리 or 검색 등) 상품 목록 조회
    @GetMapping("/admin/itemList")
    public ItemDto.SimpleItemPageAdminResponse showItems(SearchDto.Items request){

        ItemDto.SimpleItemPageAdminResponse itemPageDto = adminItemService.showItems(request);

        return itemPageDto;
    }


    // 상품 상세 조회
    @GetMapping("/admin/item")
    public ItemDto.FullItemAdminResponse showItem(@RequestParam("itemId") Long itemId){

        ItemDto.FullItemAdminResponse itemDto = adminItemService.showItem(itemId);

        return itemDto;
    }


    // 상품 추가
    @PostMapping("/admin/item")
    public String saveItem(ItemDto.SaveRequest request) throws IOException {

        log.info("진입");
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


    // 상품 이미지 교체 (메인 이미지)
    @PatchMapping("/admin/item/mainImg/replace")
    public String replaceItemMainImg(ItemDto.ReplaceImgRequest request) throws IOException {

        adminItemService.replaceItemMainImg(request);

        return "메인 이미지 교체 완료";
    }


    // 상품 이미지 추가 (메인 이미지)
    @PatchMapping("/admin/item/mainImg/add")
    public String addItemMainImg(ItemDto.AddImgRequest request) throws IOException {

        adminItemService.addItemMainImg(request);

        return "메인 이미지 추가 완료";
    }


    // 상품 이미지 순서교체 (메인 이미지)
    @PatchMapping("/admin/item/mainImg/change")
    public String changeItemMainImg(@RequestBody ItemDto.ChangeImgRequest request){

        adminItemService.changeItemMainImg(request);

        return "메인 이미지 순서 교체 완료";
    }


    // 상품 이미지 삭제 (메인 이미지)
    @DeleteMapping("/admin/item/mainImg/delete")
    public String deleteItemMainImg(ItemDto.DeleteImgRequest request){

        adminItemService.deleteItemMainImg(request);

        return "메인 이미지 삭제 완료";
    }


    // 상품 이미지 교체 (서브 이미지)
    @PatchMapping("/admin/item/subImg/replace")
    public String replaceItemSubImg(ItemDto.ReplaceImgRequest request) throws IOException {

        adminItemService.replaceItemSubImg(request);

        return "메인 이미지 교체 완료";
    }


    // 상품 이미지 추가 (서브 이미지)
    @PatchMapping("/admin/item/subImg/add")
    public String addItemSubImg(ItemDto.AddImgRequest request) throws IOException {

        adminItemService.addItemSubImg(request);

        return "메인 이미지 추가 완료";
    }


    // 상품 이미지 순서교체 (서브 이미지)
    @PatchMapping("/admin/item/subImg/change")
    public String changeItemSubImg(@RequestBody ItemDto.ChangeImgRequest request){

        adminItemService.changeItemSubImg(request);

        return "메인 이미지 순서 교체 완료";
    }


    // 상품 이미지 삭제 (서브 이미지)
    @DeleteMapping("/admin/item/subImg/delete")
    public String deleteItemSubImg(ItemDto.DeleteImgRequest request){

        adminItemService.deleteItemSubImg(request);

        return "메인 이미지 삭제 완료";
    }


    // 상품 삭제
    @DeleteMapping("/admin/item")
    public String deleteItem(@RequestParam("itemId") Long itemId){

        adminItemService.deleteItem(itemId);

        return "상품 삭제 완료!";
    }
}
