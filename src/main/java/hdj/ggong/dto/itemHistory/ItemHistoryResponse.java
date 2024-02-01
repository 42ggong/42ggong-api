package hdj.ggong.dto.itemHistory;

import hdj.ggong.common.enums.KeepStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ItemHistoryResponse {

    int totalPage;

    List<ItemHistoryAttributes> itemHistoryList;

    @Getter
    @Builder
    public static class ItemHistoryAttributes {

        private String username;

        private String keepIdentifier;

        private KeepStatus keepStatus;

        private LocalDateTime recordedDate;
    }
}
