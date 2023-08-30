package hdj.ggong.service;

import hdj.ggong.domain.User;
import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.mapper.UserMapper;
import hdj.ggong.repository.UserRepository;
import hdj.ggong.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserInfoResponse getUserInfo(CustomUserDetails userDetails) {
        return userMapper.userDetailsToUserInfoResponse(userDetails);
    }

    public void givenPenaltyToUser(String username) {
        userRepository.findByUsername(username)
                .map(user -> {
                    if (user.isAccountNonPenalty()) {
                        user.givenPenalty();
                    } else {
                        user.lock();
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
