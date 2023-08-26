package hdj.ggong.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hdj.ggong.domain.User;
import hdj.ggong.mapper.UserMapper;
import hdj.ggong.repository.UserRepository;
import hdj.ggong.security.jwt.CookieUtils;
import hdj.ggong.security.jwt.JwtProvider;
import hdj.ggong.security.jwt.JwtResponseBody;
import hdj.ggong.security.oauth2.OAuth2UserProfile;
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
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserProfile oAuth2UserProfile = OAuth2UserProfile.builder()
                .username((String) attributes.get("login"))
                .build();
        User user = userRepository.findByUsername(oAuth2UserProfile.getUsername())
                .orElseGet(() -> userRepository.save(userMapper.userOAuth2ProfileToUser(oAuth2UserProfile)));

        String accessToken = jwtProvider.generateAccessTokenByUser(user);
        String refreshToken = jwtProvider.generateRefreshTokenByUser(user);

        JwtResponseBody body = JwtResponseBody.builder()
                .accessToken(accessToken)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(body));

        Cookie refreshCookie = cookieUtils.getRefreshTokenCookie("refresh", refreshToken);
        response.addCookie(refreshCookie);
    }
}