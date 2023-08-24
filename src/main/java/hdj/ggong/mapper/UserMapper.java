package hdj.ggong.mapper;

import hdj.ggong.common.Role;
import hdj.ggong.domain.User;
import hdj.ggong.security.oauth2.OAuth2UserProfile;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapper {

    public User userOAuthProfileToUser(OAuth2UserProfile OAuth2UserProfile) {
        return User.builder()
                .username(OAuth2UserProfile.getUsername())
                .role(Role.ROLE_USER)
                .build();
    }
}
