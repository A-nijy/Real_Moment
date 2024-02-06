package project1.shop.jwt.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.MemberDto;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        System.out.println("PrincipalDetailsService의 loadUserByUsername() 실행");


        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("해당 아이디는 존재하지 않습니다."));

        MemberDto.ServerCheckDto memberServerDto = new MemberDto.ServerCheckDto(member);

        return new CustomUserDetails(memberServerDto);
    }
}
