package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.adminService.AdminMemberService;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminMemberController {

    private final AdminMemberService adminMemberService;


    // 회원 목록 조회
    @GetMapping("/admin/members")
    public List<MemberDto.SimpleProfileResponse> showMembers(SearchDto.Members request){

        List<MemberDto.SimpleProfileResponse> membersDto = adminMemberService.showMembers(request);

        return membersDto;
    }


    // 특정 회원 상세 조회
    @GetMapping("/admin/member")
    public MemberDto.FullProfileResponse showMember(@RequestParam("memberId") Long memberId){

        MemberDto.FullProfileResponse memberDto = adminMemberService.showMember(memberId);

        return memberDto;
    }
}