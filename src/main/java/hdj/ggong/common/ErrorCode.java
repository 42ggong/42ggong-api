package hdj.ggong.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "USER-01", "Username not found::");

    private final Integer status;

    private final String code;

    private final String message;
}
