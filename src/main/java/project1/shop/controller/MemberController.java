package project1.shop.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    // 아이디 중복 체크
    @PostMapping("/idCheck")
    public boolean idCheck(@Valid @RequestBody MemberDto.IdCheckRequest request, BindingResult bindingResult){

        log.info("시작");
        // 아이디 유효성 검사
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(String.format("잘못된 형식입니다. ID = (%s)", request.getLoginId()));
        }

        boolean check = memberService.idCheck(request);

        return check;
    }


//    // 닉네임 중복 체크
//    @PostMapping("/nicknameCheck")
//    public boolean nicknameCheck(@Valid @RequestBody MemberDto.NicknameCheckRequest request, BindingResult bindingResult){
//
//        // 닉네임 유효성 검사
//        if(bindingResult.hasErrors()){
//            throw new IllegalArgumentException(String.format("잘못된 형식입니다. Nickname = (%s)", request.getNcikname()));
//        }
//
//        boolean check = memberService.nicknameCheck(request);
//
//        return check;
//    }


    // 회원가입
    @PostMapping("/member/join")
    public void memberJoin(@Valid @RequestBody MemberDto.CreateRequest request, BindingResult bindingResult){

        // 회원가입 유효성 검사
        if(bindingResult.hasErrors()){
            log.info("{}", bindingResult);
            throw new IllegalArgumentException("형식에 알맞게 입력해주세요");
        }

        memberService.memberJoin(request);
    }


    // 로그인
    @PostMapping("/member/login")
    public void memberLogin(@RequestBody MemberDto.LoginRequest request, HttpServletResponse response){

        memberService.memberLogin(request, response);
    }


    // 로그아웃
    @PostMapping("/member/logout/{id}")
    public void memberLogout(@PathVariable Long id){

        memberService.memberLogout(id);
    }


    // 회원 정보 관리 페이지 이동
    @GetMapping("/member/{id}/profile")
    public MemberDto.profileResponse memberProfile(@PathVariable Long id){

        log.info("시작");
        MemberDto.profileResponse profileDto = memberService.memberProfile(id);

        return profileDto;
    }


    // 비밀번호 수정
    @PostMapping("/member/{id}/updatePassword")
    public void memberUpdatePassword(@PathVariable Long id, @Valid @RequestBody MemberDto.UpdatePasswordRequest request, BindingResult bindingResult){

        // 비밀번호 유효성 검사
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(String.format("잘못된 형식입니다. ID = (%s)", request.getLoginPassword()));
        }

        memberService.memberUpdatePassword(id, request);
    }


    // 개인정보 수정
    @PostMapping("/member/{id}/updateProfile")
    public void memberUpdateProfile(@PathVariable Long id, @Valid @RequestBody MemberDto.UpdateProfileRequest request, BindingResult bindingResult){

        // 개인정보 수정 유효성 검사
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException("잘못된 형식입니다. ID = (%s)");
        }

        memberService.memberUpdateProfile(id, request);
    }


    //--------------------------------------------------------------------
    // access 토큰 재발급 (+ RTR 방식으로 refresh 토큰도 재발급됨)
    @GetMapping("/reissue/accessToken")
    public String reissueAccessTokoen(HttpServletRequest request, HttpServletResponse response){

        log.info("refresh 토큰 재요청중 / access 토큰 재발급");
        memberService.reissueAccessToken(request, response);

        return "access 토큰 재발급 완료";
    }
    // -------------------------------------------------------------------

    //----------------------------------------------------------------------
    // 접근 권한 테스크
    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    // manager, admin 권한 접근 가능
    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    // admin 권한 접근 가능
    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }
    //----------------------------------------------------------------------


}
