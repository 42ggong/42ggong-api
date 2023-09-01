package hdj.ggong.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeepStatus {

    KEEP("KEEP"),
    PULLOUT("PULLOUT"),
    DISUSED("DISUSED")
    ;

    private final String keepStatus;
}
