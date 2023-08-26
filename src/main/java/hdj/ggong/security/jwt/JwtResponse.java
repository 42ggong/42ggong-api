package hdj.ggong.security.jwt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtResponse {

    private final String accessToken;

    private final String refreshToken;

}
