package hdj.ggong.service;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserInfoResponse getUserInfo(CustomUserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        return UserInfoResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
