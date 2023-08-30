package hdj.ggong.controller;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    UserInfoResponse getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserInfo(userDetails);
    }

    // TODO: 누가 penalty를 줄 수 있는가?
    @PutMapping("/users/{username}/penalty")
    void givenPenaltyToUser(@PathVariable("username") String username) {
        userService.givenPenaltyToUser(username);
    }
}
