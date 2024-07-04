package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Member;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.dto.innerDto.MemberDto;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public MemberDto.MemberPageResponse showMembers(SearchDto.Members request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Member> members = memberRepository.searchMembers(request, pageRequest);

        List<MemberDto.SimpleProfileResponse> membersDto = members.stream()
                .map(MemberDto.SimpleProfileResponse::new)
                .collect(Collectors.toList());

        MemberDto.MemberPageResponse memberPageDto = new MemberDto.MemberPageResponse(membersDto, members.getTotalPages(), request.getNowPage());

        return memberPageDto;
    }


    @Transactional
    public MemberDto.FullProfileResponse showMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        MemberDto.FullProfileResponse memberDto = new MemberDto.FullProfileResponse(member);

        return memberDto;
    }
}
