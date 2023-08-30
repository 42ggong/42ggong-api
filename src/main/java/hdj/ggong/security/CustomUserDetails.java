package hdj.ggong.security;

import hdj.ggong.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public String getRole() {
        return user.getRole().getRoleName();
    }

    /*
     * 해당 유저의 권한 목록
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /*
     * 계정 만료 여부
     * */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /*
     * 계정 잠김 여부
     * */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /*
     * 비밀번호 만료 여부
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /*
     * 사용자 활성화 여부
     * */
    @Override
    public boolean isEnabled() {
        return false;
    }
}