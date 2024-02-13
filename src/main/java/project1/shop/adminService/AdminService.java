package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.RefreshToken;
import project1.shop.domain.repository.AdminRepository;
import project1.shop.domain.repository.RefreshTokenRepository;
import project1.shop.dto.innerDto.AdminDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.jwt.config.util.JWTProperties;
import project1.shop.jwt.config.util.JwtFunction;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFunction jwtFunction;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public List<AdminDto.Response> showAdmins(SearchDto.Admins request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Admin> admins = adminRepository.searchAdmins(request, pageRequest);

        List<AdminDto.Response> adminsDto = admins.stream()
                                                    .map(AdminDto.Response::new)
                                                    .collect(Collectors.toList());

        return adminsDto;
    }


    @Transactional
    public AdminDto.Response showAdmin(Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        AdminDto.Response adminDto = new AdminDto.Response(admin);

        return adminDto;
    }


    @Transactional
    public AdminDto.Response updateAdmin(Long id, AdminDto.UpdateRequest request) {

        Admin admin = adminRepository.findById(request.getAdminId()).orElseThrow(IllegalArgumentException::new);

        admin.update(request);

        AdminDto.Response adminDto = new AdminDto.Response(admin);

        return adminDto;
    }

    @Transactional
    public void roleUpdateAdmin(Long id, AdminDto.RoleUpdateRequest request) {

        Admin admin = adminRepository.findById(request.getAdminId()).orElseThrow(IllegalArgumentException::new);

        admin.rolesUpdate(request);
    }


    @Transactional
    public void deleteAdmin(Long id, Long adminId) {

        Admin admin = adminRepository.findById(adminId).orElseThrow(IllegalArgumentException::new);

        admin.delete();
    }


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
