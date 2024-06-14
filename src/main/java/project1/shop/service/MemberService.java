package project1.shop.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.*;
import project1.shop.dto.innerDto.GradeDto;
import project1.shop.dto.innerDto.MemberDto;
import project1.shop.dto.innerDto.PointDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.jwt.config.util.JWTProperties;
import project1.shop.jwt.config.util.JwtFunction;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final GradeRepository gradeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFunction jwtFunction;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PointRepository pointRepository;


    // 아이디 중복 체크
    @Transactional
    public boolean idCheck(MemberDto.IdCheckRequest request) {

        Member member = memberRepository.findByLoginId(request.getLoginId()).orElse(null);
        Admin admin = adminRepository.findByLoginId(request.getLoginId()).orElse(null);

        return member == null && admin == null;
    }


    // 회원 가입
    @Transactional
    public void memberJoin(MemberDto.CreateRequest request) {

        Member memberId = memberRepository.findByLoginId(request.getLoginId()).orElse(null);
        Admin adminId = adminRepository.findByLoginId(request.getLoginId()).orElse(null);

        if(!(memberId == null && adminId == null)){
            throw new IllegalArgumentException("아이디가 중복입니다.");
        }

        request.setLoginPassword(bCryptPasswordEncoder.encode(request.getLoginPassword()));
        request.setRoles("ROLE_USER");

        Grade grade = gradeRepository.findByGradeName("WHITE").orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request, grade);

        memberRepository.save(member);
    }

    // 회원 로그인
    @Transactional
    public MemberDto.MemberIdResponse memberLogin(MemberDto.LoginRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByLoginId(request.getLoginId()).orElseThrow(IllegalArgumentException::new);

        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtFunction.createAccessToken(member);
        String refreshToken = jwtFunction.createRefreshToken(member);

        RefreshToken checkRefreshTooken = refreshTokenRepository.findByLoginId(member.getLoginId()).orElse(null);

        if(checkRefreshTooken != null){
            checkRefreshTooken.updateToken(refreshToken);
        } else {
            RefreshToken refreshTokenEntity = new RefreshToken(member.getLoginId(), refreshToken);
            refreshTokenRepository.save(refreshTokenEntity);
        }

        response.addHeader(JWTProperties.HEADER_STRING, accessToken);
        response.addHeader(JWTProperties.REFRESH_STRING, refreshToken);

        member.loginStatus();

        MemberDto.MemberIdResponse memberId = new MemberDto.MemberIdResponse(member.getMemberId());

        return memberId;
    }

    // 회원 로그아웃
    @Transactional
    public void memberLogout(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        RefreshToken refreshToken = refreshTokenRepository.findByLoginId(member.getLoginId()).orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(refreshToken);
    }

    // 포인트 내역 조회
    public PointDto.PointHistoryListResponse showPointHistory(Long id, SearchDto.Page request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<Point> points = pointRepository.searchPointHistory(id, pageRequest);

        List<PointDto.PointHistoryResponse> pointsDto = points.stream()
                .map(PointDto.PointHistoryResponse::new)
                .collect(Collectors.toList());

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        PointDto.PointHistoryListResponse response = new PointDto.PointHistoryListResponse(pointsDto, member.getPoint(), points.getTotalPages(), request.getNowPage());

        return response;
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
        log.info("2");
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        log.info("3");
        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        log.info("4");
        request.setNewLoginPassword(bCryptPasswordEncoder.encode(request.getNewLoginPassword()));
        log.info("5");
        member.UpdatePassword(request);
    }


    // 개인정보 이메일 수정
    @Transactional
    public void memberUpdateEmail(Long id, MemberDto.UpdateEmailRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateEmail(request.getEmail());
    }


    // 개인정보 이름 수정
    @Transactional
    public void memberUpdateName(Long id, MemberDto.UpdateNameRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateName(request.getName());
    }


    // 개인정보 생년월일 수정
    @Transactional
    public void memberUpdateBirth(Long id, MemberDto.UpdateBirthRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateBirth(request.getBirthDate());
    }


    // 개인정보 전화번호 수정
    @Transactional
    public void memberUpdateTel(Long id, MemberDto.UpdateTelRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        member.UpdateTel(request.getTel());
    }


    // 회원 탈퇴
    @Transactional
    public void memberDelete(Long id, MemberDto.PasswordRequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), member.getLoginPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByLoginId(member.getLoginId()).orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(refreshToken);

        member.DeleteMember();
    }



    //-----------------------------------------------
    // 토큰 재발급
    @Transactional
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String header_refresh = request.getHeader(JWTProperties.REFRESH_STRING);

        RefreshToken refresh = refreshTokenRepository.findByToken(header_refresh).orElseThrow(() -> new IllegalArgumentException("해당 refresh토큰이 DB에 존재하지 않습니다."));

        Member member = memberRepository.findByLoginId(refresh.getLoginId()).orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        String accessToken = jwtFunction.createAccessToken(member);
        String refreshToken = jwtFunction.createRefreshToken(member);

        refresh.updateToken(refreshToken);

        response.addHeader(JWTProperties.HEADER_STRING, accessToken);
        response.addHeader(JWTProperties.REFRESH_STRING, refreshToken);
    }

}
