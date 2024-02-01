package hdj.ggong.dto.item;

import hdj.ggong.common.enums.KeepStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateItemResponse {

    private String keepIdentifier;

    private String description;

    private KeepStatus keepStatus;

    private String keepExpiryDate;
}
