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

    public boolean getIsAccountNonPenalty() {
        return user.isAccountNonPenalty();
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
     * true: 만료 안됨
     * false: 만료됨
     * */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /*
     * 계정 잠김 여부
     * true: 잠기지 않음
     * false: 잠김
     * */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /*
     * 비밀번호 만료 여부
     * true: 만료 안됨
     * false: 만료됨
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /*
     * 사용자 활성화 여부
     * true: 활성화
     * false: 비활성화
     * */
    @Override
    public boolean isEnabled() {
        return false;
    }
}