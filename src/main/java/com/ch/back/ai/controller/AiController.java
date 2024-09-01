package com.ch.back.ai.controller;

import com.ch.back.ai.controller.request.ClientRequest;
import com.ch.back.ai.controller.response.ClientResponse;
import com.ch.back.ai.domain.AiService;
import com.ch.back.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping
    public ApiResponse<?> createDescription(@RequestBody ClientRequest clientRequest) {
        log.info("Ai 호출!: {}", clientRequest);
        ClientResponse data = aiService.createDescription(clientRequest);
        return ApiResponse.success(data);
    }

}
