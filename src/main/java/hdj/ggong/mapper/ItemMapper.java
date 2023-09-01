package hdj.ggong.mapper;

import hdj.ggong.common.enums.KeepStatus;
import hdj.ggong.common.utils.TimeUtil;
import hdj.ggong.domain.Item;
import hdj.ggong.domain.User;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final TimeUtil timeUtil;

    public Item createItemRequestToItem(User user, CreateItemRequest itemRequest, String keepIdentifier) {
        return Item.builder()
                .user(user)
                .keepIdentifier(keepIdentifier)
                .description(itemRequest.getDescription())
                .keepStatus(KeepStatus.KEEP)
                .createAt(timeUtil.getCurrentDate())
                .build();
    }

    public CreateItemResponse ItemToCreateItemResponse(Item item) {
        return CreateItemResponse.builder()
                .keepIdentifier(item.getKeepIdentifier())
                .description(item.getDescription())
                .keepStatus(item.getKeepStatus())
                .keepExpiryDate(localDateToMMDD(item.getKeepExpiryDate()))
                .build();
    }

    public ItemInfoResponse ItemToItemInfoResponse(Item item) {
        return ItemInfoResponse.builder()
                .keepIdentifier(item.getKeepIdentifier())
                .description(item.getDescription())
                .keepStatus(item.getKeepStatus())
                .keepExpiryDate(localDateToMMDD(item.getKeepExpiryDate()))
                .build();
    }

    private String localDateToMMDD(LocalDateTime localDateTime) {
        String MM = String.format("%02d", localDateTime.getMonthValue());
        String DD = String.format("%02d", localDateTime.getDayOfMonth());
        return MM + DD;
    }

}
