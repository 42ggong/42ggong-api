package hdj.ggong.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.getBearerTokenFromRequest(request);
        if (!token.isEmpty() && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUsernameFromToken(token);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(username, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authentication.setAuthenticated(true);
        }
        filterChain.doFilter(request, response);
    }
}
