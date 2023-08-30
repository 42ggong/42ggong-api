package hdj.ggong.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hdj.ggong.common.enums.ErrorCode;
import hdj.ggong.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            doFilter(request, response, filterChain);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            sendErrorResponse(response, ErrorCode.USERNAME_NOT_FOUND, usernameNotFoundException);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, Exception e) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage() + e)
                .build();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
