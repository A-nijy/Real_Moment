package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.adminService.AdminItemQAService;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.dto.innerDto.SearchDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminItemQAController {

    private final AdminItemQAService adminItemQAService;


    // 모든 상품 Q&A 목록 보기
    @GetMapping("/admin/QAList/view")
    public ItemQADto.ItemQAPageResponse showQAList(SearchDto.AdminItemQA request){

        ItemQADto.ItemQAPageResponse itemQAPageDto = adminItemQAService.showQAList(request);

        return itemQAPageDto;
    }


}
