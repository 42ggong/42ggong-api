package hdj.ggong.mapper;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInfoResponse userDetailsToUserInfoResponse(CustomUserDetails userDetails) {
        return UserInfoResponse.builder()
                .username(userDetails.getUsername())
                .benefitPoint(userDetails.getBenefitPoint())
                .penaltyPoint(userDetails.getPenaltyPoint())
                .build();
    }
}
