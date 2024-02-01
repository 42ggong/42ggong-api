package hdj.ggong.dto.item;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class CreateItemRequest {

    @Size(min = 0, max = 6, message = "설명은 최대 6자까지 입력 가능합니다")
    private String description;

}
