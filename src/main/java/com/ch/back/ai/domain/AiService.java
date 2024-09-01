package com.ch.back.ai.domain;

import com.ch.back.ai.controller.request.AiRequest;
import com.ch.back.ai.controller.request.ClientRequest;
import com.ch.back.ai.controller.response.AiResponse;
import com.ch.back.ai.controller.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;
    @Value("${gemini.api.url}")
    private String geminiUrl;

    private final RestTemplate restTemplate;

    public ClientResponse createDescription(ClientRequest clientRequest) {
        String requestUrl = geminiUrl + "?key=" + geminiApiKey;
        AiRequest chatRequest = new AiRequest(clientRequest.question());
        AiResponse response = restTemplate.postForObject(requestUrl, chatRequest, AiResponse.class);
        String jsonResponse = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

        log.info("AI Service jsonResponse: {}", jsonResponse);
        ClientResponse clientResponse = ClientResponse.fromJson(jsonResponse);


        return clientResponse;
    }
}
