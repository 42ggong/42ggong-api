package hdj.ggong.controller;

import hdj.ggong.dto.user.UserInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
