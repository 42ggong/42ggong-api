package hdj.ggong.security.oauth2;

import hdj.ggong.domain.User;
import hdj.ggong.mapper.UserMapper;
import hdj.ggong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        User user = saveOrGetUser(oAuth2User.getName());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName())),
                attributes,
                userNameAttributeName);
    }

    private User saveOrGetUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    OAuth2UserProfile oAuth2UserProfile = OAuth2UserProfile.builder()
                            .username(username)
                            .build();
                    return userRepository.save(userMapper.userOAuthProfileToUser(oAuth2UserProfile));
                });
    }

}
