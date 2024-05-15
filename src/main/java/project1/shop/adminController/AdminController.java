package project1.shop.adminController;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminService;
import project1.shop.dto.innerDto.AdminDto;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;


    // 아이디 중복 체크
    @PostMapping("/adminIdCheck")
    public boolean idCheck(@Valid @RequestBody AdminDto.IdCheckRequest request, BindingResult bindingResult){

        log.info("시작");
        // 아이디 유효성 검사
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(String.format("잘못된 형식입니다. ID = (%s)", request.getLoginId()));
        }

        boolean check = adminService.idCheck(request);

        return check;
    }


    // 관리자 가입
    @PostMapping("/adminJoin")
    public String adminJoin(@Valid @RequestBody AdminDto.CreateRequest request, BindingResult bindingResult){

        // 관리자 가입 유효성 검사
        if(bindingResult.hasErrors()){
            log.info("{}", bindingResult);
            throw new IllegalArgumentException("형식에 알맞게 입력해주세요");
        }

        adminService.createAdmin(request);

        return "관리자 가입 완료!";
    }

    // 관리자 로그인
    @PostMapping("/adminLogin")
    public AdminDto.AdminIdResponse adminLogin(@RequestBody AdminDto.LoginRequest request, HttpServletResponse response){

        AdminDto.AdminIdResponse adminId = adminService.adminLogin(request, response);

        return adminId;
    }

    // 관리자 로그아웃
    @PostMapping("/admin/{id}/logout")
    public String adminLogout(@PathVariable Long id){

        adminService.adminLogout(id);

        return "관리자 로그아웃 완료!";
    }



    // 관리자 목록 조회
    @GetMapping("/admin/adminList")
    public AdminDto.PageResponse showAdmins(SearchDto.Admins request){

        AdminDto.PageResponse adminPageDto = adminService.showAdmins(request);

        return adminPageDto;
    }


    // 관리자 상세 정보 조회
    @GetMapping("/admin/admin")
    public AdminDto.Response showAdmin(@RequestParam("adminId") Long adminId){

        AdminDto.Response adminDto = adminService.showAdmin(adminId);

        return adminDto;
    }


    // 관리자 본인 정보 수정
    @PatchMapping("/admin/{id}")
    public AdminDto.Response updateAdmin(@PathVariable Long id, @RequestBody AdminDto.UpdateRequest request){

        AdminDto.Response adminDto = adminService.updateAdmin(id, request);

        return adminDto;
    }


    // 관리자 권한 수정
    @PatchMapping("/admin/admin/roles")
    public String roleUpdateAdmin(@RequestBody AdminDto.RoleUpdateRequest request){

        adminService.roleUpdateAdmin(request);

        return "관리자 권한 수정 완료!";
    }


    // 관리자 삭제 (고위 관리자)
    @PatchMapping("/admin/admin")
    public String deleteAdmin(@RequestBody AdminDto.AdminIdRequest request){

        adminService.deleteAdmin(request.getAdminId());

        return "관리자 삭제 완료!";
    }



    //--------------------------------------------------------------------
    // access 토큰 재발급 (+ RTR 방식으로 refresh 토큰도 재발급됨)
    @PostMapping("/admin/reissue/accessToken")
    public String reissueAccessTokoen(HttpServletRequest request, HttpServletResponse response){

        log.info("refresh 토큰 재요청중 / access 토큰 재발급");
        adminService.reissueAccessToken(request, response);

        return "access 토큰 재발급 완료";
    }
    // -------------------------------------------------------------------
}
