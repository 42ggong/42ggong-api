package hdj.ggong.dto.item;

import hdj.ggong.common.enums.KeepStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItemInfoResponse {

    private String keepIdentifier;

    private String description;

    private KeepStatus keepStatus;

    private LocalDateTime keepExpiryDate;
}
