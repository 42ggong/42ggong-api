package hdj.ggong.mapper;

import hdj.ggong.common.enums.KeepStatus;
import hdj.ggong.common.utils.TimeUtil;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.User;
import hdj.ggong.dto.item.CreateItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final TimeUtil timeUtil;

    public Item createItemRequestToItem(User user, CreateItemRequest itemRequest) {
        return Item.builder()
                .user(user)
                .keepIdentifier(generateKeepIdentifier())
                .description(itemRequest.getDescription())
                .keepStatus(KeepStatus.STATUS_KEEP)
                .createAt(timeUtil.getCurrentDate())
                .build();
    }

    private String generateKeepIdentifier() {
        // TODO: 6자 랜덤 문자열 생성
        return "";
    }

}
