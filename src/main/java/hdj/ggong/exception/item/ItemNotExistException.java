package hdj.ggong.exception.item;

import hdj.ggong.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ItemNotExistException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.ITEM_NOT_EXIST;

    public ItemNotExistException(Long itemId) {
        super("존재하지 않는 아이템: " + itemId);
    }

}
