package project1.shop.jwt.config.auth;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project1.shop.dto.commonDto.ServerCheckDto;
import project1.shop.dto.innerDto.MemberDto;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final ServerCheckDto serverCheckDto;

    //------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        serverCheckDto.getRoleList().forEach(r -> {
            authorities.add(() -> r);
        });

        return authorities;
    }

    //--------------------------------------------------

    @Override
    public String getPassword() {
        return serverCheckDto.getLoginPassword();
    }

    @Override
    public String getUsername() {
        return serverCheckDto.getLoginId();
    }

    //----------------------------------------------------

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
