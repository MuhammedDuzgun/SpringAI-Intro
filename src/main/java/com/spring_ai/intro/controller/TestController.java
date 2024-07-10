package com.spring_ai.intro.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class TestController {

    private final ChatClient chatClient;

    public TestController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    String generation(@RequestBody String userInput) {
        String response = chatClient.prompt()
                .user(userInput)
                .call()
                .content();
        return response;
    }

}
