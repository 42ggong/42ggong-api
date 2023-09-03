package hdj.ggong.dto.itemHistory;

import hdj.ggong.common.enums.KeepStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItemHistoryResponse {

    private String username;

    private String keepIdentifier;

    private KeepStatus keepStatus;

    private LocalDateTime recordedDate;
}
