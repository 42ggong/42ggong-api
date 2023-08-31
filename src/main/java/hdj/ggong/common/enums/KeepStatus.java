package hdj.ggong.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeepStatus {

    STATUS_KEEP("STATUS_KEEP"),
    STATUS_PULL("STATUS_PULL"),
    STATUS_DISUSED("STATUS_DISUSED")
    ;

    private final String keepStatus;
}
