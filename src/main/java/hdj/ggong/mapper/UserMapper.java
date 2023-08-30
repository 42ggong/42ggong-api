package hdj.ggong.mapper;

import hdj.ggong.common.Role;
import hdj.ggong.domain.User;
import hdj.ggong.security.oauth2.OAuth2UserProfile;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapper {

    public User userOAuth2ProfileToUser(OAuth2UserProfile oAuth2UserProfile) {
        return User.builder()
                .username(oAuth2UserProfile.getUsername())
                .role(Role.ROLE_USER)
                .benefitPoint(0L)
                .isAccountNonPenalty(false)
                .isAccountNonLocked(false)
                .build();
    }
}
