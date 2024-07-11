package com.spring_ai.intro.controller;

import com.spring_ai.intro.output.MusicArtist;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
public class TestController {

    private final ChatClient chatClient;

    public TestController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("Kullanıcıdan gelen müzik sanatçısına benzer sanatçıları json listesi olarak" +
                        "sıralayan bir asistansın.")
                .build();
    }

    @GetMapping
    public String generation(@RequestBody String userInput) {
        String response = chatClient.prompt()
                .user(userInput)
                .call()
                .content();
        return response;
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestBody String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }

    @GetMapping("/music-artist")
    public MusicArtist getSimilarArtist(@RequestBody String artist) {
        MusicArtist musicArtist = chatClient.prompt()
                .user(artist)
                .call()
                .entity(MusicArtist.class);
        return musicArtist;
    }

}
