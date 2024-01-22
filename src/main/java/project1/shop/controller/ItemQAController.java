package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.service.ItemQAService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemQAController {

    private final ItemQAService itemQAService;


    // 특정 상품 문의 목록 조회
    @GetMapping("/QAList")
    public List<ItemQADto.ItemQAResponse> showQAList(@RequestParam("itemId") Long itemId){

        List<ItemQADto.ItemQAResponse> QAListDto = itemQAService.showQAList(itemId);

        return QAListDto;
    }


    // 내가 작성한 상품 문의 목록 조회
    @GetMapping("/QAList/{id}")
    public List<ItemQADto.MyItemQAResponse> showMyQAList(@PathVariable Long id){

        List<ItemQADto.MyItemQAResponse> MyQAListDto = itemQAService.showMyQAList(id);

        return MyQAListDto;
    }


    // 상품 문의 작성하기
    @PostMapping("/QA/{id}")
    public void saveItemQA(@PathVariable Long id, @RequestBody ItemQADto.ItemQARequest request){

        itemQAService.saveItemQA(id, request);
    }


    // 상품 문의 수정하기
    @PatchMapping("/QA/{id}")
    public void updateItemQA(@PathVariable Long id, @RequestBody ItemQADto.UpdateItemQARequest request){
        log.info("서비스 호출");
        itemQAService.updateItemQA(id, request);
    }


    // 상품 문의 삭제하기
    @DeleteMapping("/QA/{id}")
    public void deleteItemQA(@PathVariable Long id, @RequestParam("itemQAId") Long itemQAId){

        itemQAService.deleteItemQA(id, itemQAId);
    }
}
