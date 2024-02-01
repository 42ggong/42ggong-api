package hdj.ggong.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private String username;

    private int benefitPoint;

    private int penaltyPoint;
}
