package project1.shop.adminController;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminService;
import project1.shop.dto.innerDto.AdminDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

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



    // 관리자 목록 조회
    @GetMapping("/admin/admins")
    public List<AdminDto.Response> showAdmins(SearchDto.Admins request){

        List<AdminDto.Response> adminsDto = adminService.showAdmins(request);

        return adminsDto;
    }


    // 관리자 상세 정보 조회
    @GetMapping("/admin/admin/{id}")
    public AdminDto.Response showAdmin(@PathVariable Long id){

        AdminDto.Response adminDto = adminService.showAdmin(id);

        return adminDto;
    }


    // 관리자 본인 정보 수정
    @PatchMapping("/admin/admin/{id}")
    public AdminDto.Response updateAdmin(@PathVariable Long id, @RequestBody AdminDto.UpdateRequest request){

        AdminDto.Response adminDto = adminService.updateAdmin(id, request);

        return adminDto;
    }


    // 관리자 권한 수정
    @PatchMapping("/admin/admin/roles/{id}")
    public String roleUpdateAdmin(@PathVariable Long id, @RequestBody AdminDto.RoleUpdateRequest request){

        adminService.roleUpdateAdmin(id, request);

        return "관리자 권한 수정 완료!";
    }


    // 관리자 삭제 (고위 관리자)
    @DeleteMapping("/admin/admin/{id}")
    public String deleteAdmin(@PathVariable Long id, @RequestParam("adminId") Long adminId){

        adminService.deleteAdmin(id, adminId);

        return "관리자 삭제 완료!";
    }
}
