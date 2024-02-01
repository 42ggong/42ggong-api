package hdj.ggong.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class JwtResponseBody {

    private final String accessToken;

}
