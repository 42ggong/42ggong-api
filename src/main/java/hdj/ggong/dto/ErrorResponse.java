package hdj.ggong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final Integer status;

    private final String code;

    private final String message;
}
