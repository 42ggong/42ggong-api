package hdj.ggong.security.oauth2;

import hdj.ggong.common.enums.Role;
import hdj.ggong.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class OAuth2UserProfile {

    private final String username;

    private final String email;
    private final String slackId;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .slackId(this.slackId)
                .role(Role.ROLE_USER)
                .benefitPoint(0)
                .penaltyPoint(0)
                .isAccountNonPenalty(true)
                .isAccountNonLocked(true)
                .build();
    }
}
