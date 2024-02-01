package hdj.ggong.mapper;


import hdj.ggong.common.enums.Role;
import hdj.ggong.domain.ItemHistory;
import hdj.ggong.dto.itemHistory.ItemHistoryResponse;
import hdj.ggong.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemHistoryMapper {

    private ItemHistoryResponse.ItemHistoryAttributes itemHistoryPageToItemHistoryAttributes(ItemHistory itemHistory, Role role) {
        ItemHistoryResponse.ItemHistoryAttributes.ItemHistoryAttributesBuilder itemHistoryResponseBuilder = ItemHistoryResponse.ItemHistoryAttributes.builder()
                .keepIdentifier(itemHistory.getItem().getKeepIdentifier())
                .keepStatus(itemHistory.getItem().getKeepStatus())
                .recordedDate(itemHistory.getRecordedDate());
        if (role.equals(Role.ROLE_ADMIN)) {
            itemHistoryResponseBuilder
                    .username(itemHistory.getUser().getUsername());
        }
        return itemHistoryResponseBuilder.build();
    }

    public ItemHistoryResponse ItemHistoryPageToItemHistoryResponse(CustomUserDetails userDetails, Page<ItemHistory> itemHistoryPage) {
        int totalPage = itemHistoryPage.getTotalPages();
        List<ItemHistoryResponse.ItemHistoryAttributes> itemHistoryList = itemHistoryPage.stream()
                .map(itemHistory -> itemHistoryPageToItemHistoryAttributes(itemHistory, userDetails.getRole())
                )
                .toList();
        return ItemHistoryResponse.builder()
                .totalPage(totalPage)
                .itemHistoryList(itemHistoryList)
                .build();
    }
}
