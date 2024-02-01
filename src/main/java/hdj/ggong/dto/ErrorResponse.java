package hdj.ggong.dto;

import hdj.ggong.common.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {

    private final Integer status;

    private final String code;

    private final String message;

}
