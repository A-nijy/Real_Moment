package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.adminService.AdminService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;


    // 로그인 (보류 - refresh 토큰을 회원, 관리자용으로 두 개 만들어야 하나..)
//    @PostMapping("/adminLogin")
//    public String adminLogin(@RequestBody AdminDto.LoginRequest request){
//
//        adminService.adminLogin(request);
//
//        return "로그인 완료!!";
//    }


}
