package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.service.ItemQAService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemQAController {

    private final ItemQAService itemQAService;



    // 내가 작성한 상품 문의 목록 조회
    @GetMapping("/member/{id}/QAList")
    public List<ItemQADto.MyItemQAResponse> showMyQAList(@PathVariable Long id, SearchDto.Page nowPage){

        List<ItemQADto.MyItemQAResponse> myItemQADto = itemQAService.showMyQAList(id, nowPage);

        return myItemQADto;
    }


    // 상품 문의 작성하기
    @PostMapping("/member/{id}/QA")
    public void saveItemQA(@PathVariable Long id, @RequestBody ItemQADto.ItemQARequest request){

        itemQAService.saveItemQA(id, request);
    }


    // 상품 문의 수정하기
    @PatchMapping("/member/{id}/QA")
    public void updateItemQA(@PathVariable Long id, @RequestBody ItemQADto.UpdateItemQARequest request){
        log.info("서비스 호출");
        itemQAService.updateItemQA(id, request);
    }


    // 상품 문의 삭제하기
    @DeleteMapping("/member/{id}/QA")
    public void deleteItemQA(@PathVariable Long id, @RequestParam("itemQAId") Long itemQAId){

        itemQAService.deleteItemQA(id, itemQAId);
    }
}
