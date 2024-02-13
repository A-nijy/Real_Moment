package project1.shop.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.RefreshToken;
import project1.shop.domain.repository.GradeRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.RefreshTokenRepository;
import project1.shop.dto.innerDto.GradeDto;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.jwt.config.util.JWTProperties;
import project1.shop.jwt.config.util.JwtFunction;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFunction jwtFunction;
    private final RefreshTokenRepository refreshTokenRepository;


    // 아이디 중복 체크
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

    // 회원 가입
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
        request.setLoginPassword(bCryptPasswordEncoder.encode(request.getLoginPassword()));
        request.setRoles("ROLE_USER");

        Grade grade = gradeRepository.findByGradeName("WHITE").orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request, grade);

        memberRepository.save(member);
    }

    // 회원 로그인
    @Transactional
    public void memberLogin(MemberDto.LoginRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByLoginId(request.getLoginId()).orElseThrow(IllegalArgumentException::new);

        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtFunction.createAccessToken(member);
        String refreshToken = jwtFunction.createRefreshToken(member);

        RefreshToken checkRefreshTooken = refreshTokenRepository.findByMemberId(member.getMemberId()).orElse(null);

        if(checkRefreshTooken != null){
            checkRefreshTooken.updateToken(refreshToken);
        } else {
            RefreshToken refreshTokenEntity = new RefreshToken(member.getMemberId(), refreshToken);
            refreshTokenRepository.save(refreshTokenEntity);
        }

        response.addHeader(JWTProperties.HEADER_STRING, accessToken);
        response.addHeader(JWTProperties.REFRESH_STRING, refreshToken);

        member.loginStatus();
    }

    // 회원 로그아웃
    @Transactional
    public void memberLogout(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(member.getMemberId()).orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(refreshToken);
//        member.logoutStatus();
    }

    // 회원 정보 가져오기 (비밀번호 제외)
    @Transactional
    public MemberDto.ProfileResponse memberProfile(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        GradeDto.Response gradeDto = new GradeDto.Response(member.getGrade());

        MemberDto.ProfileResponse profileDto = new MemberDto.ProfileResponse(member, gradeDto);

        return profileDto;
    }

    // 회원 비밀번호 수정
    @Transactional
    public void memberUpdatePassword(Long id, MemberDto.UpdatePasswordRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        request.setNewLoginPassword(bCryptPasswordEncoder.encode(request.getNewLoginPassword()));

        member.UpdatePassword(request);
    }

    // 회원 프로필 수정
    @Transactional
    public void memberUpdateProfile(Long id, MemberDto.UpdateProfileRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateProfile(request);
    }


    // 회원 탈퇴
    @Transactional
    public void memberDelete(Long id, MemberDto.PasswordRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(member.getMemberId()).orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(refreshToken);

        member.DeleteMember();
    }



    //-----------------------------------------------
    // 토큰 재발급
    @Transactional
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String header_refresh = request.getHeader(JWTProperties.REFRESH_STRING);

        RefreshToken refresh = refreshTokenRepository.findByToken(header_refresh).orElseThrow(() -> new IllegalArgumentException("해당 refresh토큰이 DB에 존재하지 않습니다."));

        Member member = memberRepository.findById(refresh.getMemberId()).orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        String accessToken = jwtFunction.createAccessToken(member);
        String refreshToken = jwtFunction.createRefreshToken(member);

        refresh.updateToken(refreshToken);

        response.addHeader(JWTProperties.HEADER_STRING, accessToken);
        response.addHeader(JWTProperties.REFRESH_STRING, refreshToken);
    }


}
