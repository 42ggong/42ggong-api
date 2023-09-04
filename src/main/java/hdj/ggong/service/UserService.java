package hdj.ggong.service;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.mapper.UserMapper;
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserInfoResponse getUserInfo(CustomUserDetails userDetails) {
        return userMapper.userDetailsToUserInfoResponse(userDetails);
    }

}
