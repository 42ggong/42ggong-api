package hdj.ggong.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeepStatus {

    STATUS_KEEP("STATUS_KEEP"),
    STATUS_PULL("STATUS_PULL"),
    STATUS_DISUSE("STATUS_DISUSE"),
    STATUS_RE_DISUSE("STATUS_RE_DISUSE");

    private final String keepStatus;
}
