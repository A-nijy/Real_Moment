package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public List<MemberDto.SimpleProfileResponse> showMembers(SearchDto.Members request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Member> members = memberRepository.searchMembers(request, pageRequest);

        List<MemberDto.SimpleProfileResponse> membersDto = members.stream()
                .map(MemberDto.SimpleProfileResponse::new)
                .collect(Collectors.toList());

        return membersDto;
    }


    @Transactional
    public MemberDto.FullProfileResponse showMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        MemberDto.FullProfileResponse memberDto = new MemberDto.FullProfileResponse(member);

        return memberDto;
    }
}
