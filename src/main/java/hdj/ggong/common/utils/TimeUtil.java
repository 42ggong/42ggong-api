package hdj.ggong.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeUtil {

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
