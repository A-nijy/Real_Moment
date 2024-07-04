package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.adminService.AdminMemberService;
import rm.backend.dto.innerDto.MemberDto;
import rm.backend.dto.innerDto.SearchDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;


    // 회원 목록 조회
    @GetMapping("/admin/memberList/view")
    public MemberDto.MemberPageResponse showMembers(SearchDto.Members request){

        MemberDto.MemberPageResponse memberPageDto = adminMemberService.showMembers(request);

        return memberPageDto;
    }


    // 특정 회원 상세 조회
    @GetMapping("/admin/member/view")
    public MemberDto.FullProfileResponse showMember(@RequestParam("memberId") Long memberId){

        MemberDto.FullProfileResponse memberDto = adminMemberService.showMember(memberId);

        return memberDto;
    }
}
