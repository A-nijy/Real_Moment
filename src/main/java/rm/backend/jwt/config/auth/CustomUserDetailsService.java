package rm.backend.jwt.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Admin;
import rm.backend.domain.entity.Member;
import rm.backend.domain.repository.AdminRepository;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.dto.commonDto.ServerCheckDto;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        System.out.println("PrincipalDetailsService의 loadUserByUsername() 실행");


        Member member = memberRepository.findByLoginId(loginId).orElse(null);
        Admin admin = adminRepository.findByLoginId(loginId).orElse(null);

        ServerCheckDto serverCheckDto;

        if(member == null && admin != null){
            serverCheckDto = new ServerCheckDto(admin);
        } else if(member != null && admin == null){
            serverCheckDto = new ServerCheckDto(member);
        } else {
            throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
        }


        return new CustomUserDetails(serverCheckDto);
    }
}
