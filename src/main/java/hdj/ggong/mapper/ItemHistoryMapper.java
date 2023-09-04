package hdj.ggong.mapper;


import hdj.ggong.common.enums.Role;
import hdj.ggong.domain.ItemHistory;
import hdj.ggong.dto.itemHistory.ItemHistoryResponse;
import org.springframework.stereotype.Component;

@Component
public class ItemHistoryMapper {

    public ItemHistoryResponse itemHistoryToItemHistoryResponse(ItemHistory itemHistory, Role role) {
        ItemHistoryResponse.ItemHistoryResponseBuilder itemHistoryResponseBuilder = ItemHistoryResponse.builder()
                .keepIdentifier(itemHistory.getItem().getKeepIdentifier())
                .keepStatus(itemHistory.getItem().getKeepStatus())
                .recordedDate(itemHistory.getRecordedDate());
        if (role.equals(Role.ROLE_ADMIN)) {
            itemHistoryResponseBuilder
                    .username(itemHistory.getUser().getUsername());
        }
        return itemHistoryResponseBuilder.build();
    }

}
