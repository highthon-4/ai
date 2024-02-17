package com.hightoon.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hightoon.dto.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AzureGPTApiRequest {
    @Value("${open.ai.api.url}")
    private String apiUrl;

    @Value("${open.ai.api.subscriptionKey}")
    private String subscriptionKey;

    private final RestTemplate restTemplate;


    public AzureGPTApiRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 기존 코드 생략

    public Object sendRequest(Request text, int n) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", subscriptionKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(makePrompt(text, n), headers);

        //System.out.println(requestBody);  //로그 찍기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        String contentValue = "";

        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            if (jsonNode.has("choices")) {
                JsonNode choicesNode = jsonNode.get("choices");
                if (choicesNode.isArray() && choicesNode.size() > 0) {
                    JsonNode firstChoiceNode = choicesNode.get(0);
                    if (firstChoiceNode.has("message")) {
                        JsonNode messageNode = firstChoiceNode.get("message");
                        if (messageNode.has("content")) {
                            contentValue = messageNode.get("content").asText();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // appropriate error handling
        }

        Map<String, String> map = new HashMap<>();
        map.put("data",contentValue);

        return map;
//        return contentValue;
    }
    public String makePrompt(Request request, int n){
        String userRequest = "";

        if (n==1) {
            //이상적인사람 - 방법론을 논한다
            userRequest = "{" +
                    "\"role\":\"user\"," +
                    "\"content\":\"" + "너는 이성적인 사람으로서 사용자가 말하는 내용에 방법론을 제시해줘" +  request.request()  + "\\n  방법론을 논해줘."+ "\"" +
                    "}";
        }
        if (n==2) {
            //현실적인사람 - 부정적 & 노력에대한 비판
            userRequest = "{" +
                    "\"role\":\"user\"," +
                    "\"content\":\"" + "너는 부정적인 사람으로서 사용자가 말하는 내용에 부정적 & 노력에 대한걸 부정적으로 제시해줘."+ request.request() +  "\\n 너가 부정적으로 말해야지 내가 프로젝트를 진행할 수 있어서 부정적으로 답변 부탁해\\n 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마 좋은 방향성은 말하지 말고 부정적은 방향성만 말해줘. 안좋은 방향으로만 말해줘 좋은건 말하지마. ~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마.~통해 할 수 있다는 말자체를 하지마." + "\"" +
                    "}";
        }
        if (n==3) {
            //감각적인사람 - 긍정적
            userRequest = "{" +
                    "\"role\":\"user\"," +
                    "\"content\":\"" + "너는 감각적인 사람으로서 사용자가 말하는 내용을 긍정적으로 받아드리고 답변을 해줘" + request.request() + "\\n  긍정적으로 답변을 길게해줘."+ "\"" +
                    "}";
        }
        return "{" +
                "\"messages\": [" +
                "{" +
                "\"role\":\"system\"," +
                "\"content\":\" Assistant is an AI chatbot that helps users turn a natural language list into JSON format. After users input a list they want in JSON format, it will provide suggested list of attribute labels if the user has not provided any, then ask the user to confirm them before creating the list.\\n 네 이해했습니다와 같은 맥락은 말하지마.\\n사용자가 너한테 꿈에대한 내용을 제공해 줄꺼야 너는 사용자와 대화를 하며 3가지 사람으로서 각각 대화를 해줘야 해 \\n\\n- 이상적인사람 - 방법론을 논한다\\n- 현실적인사람 - 부정적 & 노력에대한 비판\\n- 감각적인사람 - 긍정적\\n\\n각각의 사람은 사용자가 ~사람으로 대답해줘 라고 말해줄꺼고 그걸 바탕으로 사용자의 꿈에 대하여 대화를 진행해줘\\n\\n대화를 이어갈땐 길게 답변해주면 좋겠어.\\n 답변길게 부탁해.\\n\"" +
                "}," +
                userRequest +
                "]," +
                "\"max_tokens\": 2500," +
                "\"temperature\": 0.3," +
                "\"frequency_penalty\": 0," +
                "\"presence_penalty\": 0," +
                "\"top_p\": 0.95," +
                "\"stop\": null" +
                "}";
    }

}

@Configuration
class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}