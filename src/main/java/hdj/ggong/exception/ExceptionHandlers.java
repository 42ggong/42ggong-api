package hdj.ggong.exception;

import hdj.ggong.common.enums.ErrorCode;
import hdj.ggong.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorCode errorCode, String exceptionMessage) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage() + exceptionMessage)
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Handling Exception: " + exception);
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String exceptionMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return buildResponseEntity(errorCode, exceptionMessage);
    }

}
