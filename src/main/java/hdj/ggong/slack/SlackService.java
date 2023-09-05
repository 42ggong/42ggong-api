package hdj.ggong.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.token}")
    private String token;

    public void sendMessage(String id, String text) {
        try {
            Slack slack = Slack.getInstance();
            ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                    .channel(id)
                    .text(text)
            );
            if (response.isOk()) {
                Message postedMessage = response.getMessage();
                log.info("Slack " + id + " 에 메시지 보냄: " + postedMessage);
            } else {
                String errorCode = response.getError();
                log.error("Slack " + id + " 에 메시지 보냄: " + errorCode);
            }
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }

    public String getUserIdByEmail(String email) throws JsonProcessingException {
        String url = "https://slack.com/api/users.lookupByEmail";
        url += "?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> restResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode body = objectMapper.readTree(restResponse.getBody());
        if (body.get("ok").asBoolean()) {
            String userId = body.get("user").get("id").textValue();
            log.info("Found slack userId: " + userId);
            return userId;
        } else {
            log.error("Not found user: " + email);
            return null;
        }
    }
}
