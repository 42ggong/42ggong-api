package hdj.ggong.security;

import hdj.ggong.domain.User;
import hdj.ggong.mapper.UserMapper;
import hdj.ggong.repository.UserRepository;
import hdj.ggong.security.jwt.CookieUtils;
import hdj.ggong.security.jwt.JwtProvider;
import hdj.ggong.security.oauth2.OAuth2UserProfile;
import hdj.ggong.slack.SlackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final CookieUtils cookieUtils;
    private final SlackService slackService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = (String) attributes.get("login");
        String email = (String) attributes.get("email");
        String slackId = slackService.getUserIdByEmail(email);
        OAuth2UserProfile oAuth2UserProfile = OAuth2UserProfile.builder()
                .username(username)
                .email(email)
                .slackId(slackId)
                .build();
        User user = userRepository.findByUsername(oAuth2UserProfile.getUsername())
                .orElseGet(() -> userRepository.save(oAuth2UserProfile.toUser()));

        String accessToken = jwtProvider.generateAccessTokenByUser(user);
        String refreshToken = jwtProvider.generateRefreshTokenByUser(user);
        Cookie accessCookie = cookieUtils.generateAccessCookie("accessToken", accessToken);
        Cookie refreshCookie = cookieUtils.generateRefreshCookie("refreshToken", refreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        response.sendRedirect("http://localhost:3000"); // TODO: 하드 코딩. oauth2 로그인 요청한 uri
    }
}