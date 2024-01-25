package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.GradeRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.GradeDto;
import project1.shop.dto.innerDto.MemberDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;


    @Transactional
    public boolean idCheck(MemberDto.IdCheckRequest request) {

        Member member = memberRepository.findByLoginId(request.getLoginId()).orElse(null);

        return member == null;
    }

//    @Transactional
//    public boolean nicknameCheck(MemberDto.NicknameCheckRequest request) {
//
//        Member member = memberRepository.findByNickname(request.getNcikname()).orElse(null);
//
//        return member == null;
//    }

    @Transactional
    public void memberJoin(MemberDto.CreateRequest request) {

        Member memberId = memberRepository.findByLoginId(request.getLoginId()).orElse(null);

        if(!(memberId == null)){
            throw new IllegalArgumentException("아이디가 중복입니다.");
        }

//        Member memberNickname = memberRepository.findByNickname(request.getNcikname()).orElse(null);
//
//        if(!(memberNickname == null)){
//            throw new IllegalArgumentException("닉네임이 중복입니다.");
//        }

        Grade grade = gradeRepository.findByGradeName("WHITE").orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request, grade);

        memberRepository.save(member);
    }

    @Transactional
    public void memberLogin(MemberDto.LoginRequest request) {

        Member member = memberRepository.findByLoginId(request.getLoginId()).orElseThrow(IllegalArgumentException::new);

        if(!(member.getLoginPassword().equals(request.getLoginPassword()))){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        member.loginStatus();
    }

//    @Transactional
//    public void memberLogout(Long id) {
//
//        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
//
//        member.logoutStatus();
//    }

    @Transactional
    public MemberDto.profileResponse memberProfile(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        GradeDto.Response gradeDto = new GradeDto.Response(member.getGrade());

        MemberDto.profileResponse profileDto = new MemberDto.profileResponse(member, gradeDto);

        return profileDto;
    }

    @Transactional
    public void memberUpdatePassword(Long id, MemberDto.UpdatePasswordRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdatePassword(request);
    }

    @Transactional
    public void memberUpdateProfile(Long id, MemberDto.UpdateProfileRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateProfile(request);
    }
}
