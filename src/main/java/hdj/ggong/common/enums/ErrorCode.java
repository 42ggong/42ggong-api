package hdj.ggong.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "COMMON-01", "Invalid request::"),

    // User
    USERNAME_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "USER-01", "Username not found::"),

    // Item
    ITEM_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "ITEM-01", "Item not exist::");

    private final Integer status;

    private final String code;

    private final String message;
}
