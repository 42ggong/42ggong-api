package hdj.ggong.dto;

import hdj.ggong.common.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final Integer status;

    private final String code;

    private final String message;
}
