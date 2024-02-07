package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.RefreshToken;
import project1.shop.domain.repository.AdminRepository;
import project1.shop.domain.repository.RefreshTokenRepository;
import project1.shop.dto.innerDto.AdminDto;
import project1.shop.jwt.config.util.JWTProperties;
import project1.shop.jwt.config.util.JwtFunction;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFunction jwtFunction;
    private final RefreshTokenRepository refreshTokenRepository;


    // 로그인 (보류 - refresh 토큰을 회원, 관리자용으로 두 개 만들어야 하나..)
//    @Transactional
//    public void adminLogin(AdminDto.LoginRequest request) {
//
//        Admin admin = adminRepository.findByLoginId(request.getLoginId()).orElseThrow(() -> new IllegalArgumentException("해당 관리자는 존재하지 않습니다."));
//
//        if(!bCryptPasswordEncoder.matches(request.getLoginPassword(), admin.getLoginPassword())){
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        String accessToken = jwtFunction.createAccessToken(admin);
//        String refreshToken = jwtFunction.createRefreshToken(admin);
//
//        RefreshToken checkRefreshTooken = refreshTokenRepository.findByMemberId(admin.getMemberId()).orElse(null);
//
//        if(checkRefreshTooken != null){
//            checkRefreshTooken.updateToken(refreshToken);
//        } else {
//            RefreshToken refreshTokenEntity = new RefreshToken(member.getMemberId(), refreshToken);
//            refreshTokenRepository.save(refreshTokenEntity);
//        }
//
//        response.addHeader(JWTProperties.HEADER_STRING, accessToken);
//        response.addHeader(JWTProperties.REFRESH_STRING, refreshToken);
//    }
}
