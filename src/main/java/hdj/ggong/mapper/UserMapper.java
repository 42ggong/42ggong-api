package hdj.ggong.mapper;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapper {

    public UserInfoResponse userDetailsToUserInfoResponse(CustomUserDetails userDetails) {
        return UserInfoResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
