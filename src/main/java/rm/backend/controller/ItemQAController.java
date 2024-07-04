package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.dto.innerDto.ItemQADto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.service.ItemQAService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ItemQAController {

    private final ItemQAService itemQAService;



    // 상품 상세 정보에 응답할 특정 상품의 문의 목록 조회
    @GetMapping("/QAList")
    public ItemQADto.ItemQAPageResponse showItemQAList(SearchDto.ItemInItemQA request){

        ItemQADto.ItemQAPageResponse itemQAPageDto = itemQAService.showItemQAList(request);

        return itemQAPageDto;
    }



    // 내가 작성한 상품 문의 목록 조회
    @GetMapping("/member/{id}/QAList")
    public ItemQADto.MyItemQAPageResponse showMyQAList(@PathVariable Long id, SearchDto.Page nowPage){

        log.info("진입");
        ItemQADto.MyItemQAPageResponse myItemQAPageDto = itemQAService.showMyQAList(id, nowPage);

        return myItemQAPageDto;
    }


    // 상품 문의 작성하기
    @PostMapping("/member/{id}/QA")
    public void saveItemQA(@PathVariable Long id, @RequestBody ItemQADto.ItemQARequest request){

        itemQAService.saveItemQA(id, request);
    }


    // 상품 문의 수정버튼 클릭시 해당 문의 데이터 가져오기
    @GetMapping("/member/{id}/QA/data")
    public ItemQADto.UpdateItemQAResponse getItemQA(@PathVariable Long id, @RequestParam("itemQAId") Long itemQAId){

        ItemQADto.UpdateItemQAResponse itemQADto = itemQAService.getItemQA(id, itemQAId);

        return itemQADto;
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
