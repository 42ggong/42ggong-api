package hdj.ggong.security.oauth2;

import hdj.ggong.common.Role;
import hdj.ggong.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Builder
@Getter
@RequiredArgsConstructor
public class OAuth2UserProfile {

    private final String username;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .role(Role.ROLE_USER)
                .benefitPoint(0L)
                .isAccountNonPenalty(false)
                .isAccountNonLocked(false)
                .build();
    }
}
