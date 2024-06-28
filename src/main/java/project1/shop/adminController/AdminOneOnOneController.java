package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.adminService.AdminOneOnOneService;
import project1.shop.dto.innerDto.OneOnOneDto;
import project1.shop.dto.innerDto.SearchDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminOneOnOneController {

    private final AdminOneOnOneService adminOneOnOneService;


    // 1대1 문의 목록 조회
    @GetMapping("/admin/oneOnOneList/view")
    private OneOnOneDto.PageResponse showOneOnOneList(SearchDto.OneOnOneSearch request){

        OneOnOneDto.PageResponse oneOnOneDto = adminOneOnOneService.showOneOnOneList(request);

        return oneOnOneDto;
    }
}
