package com.spring_ai.intro.controller;

import com.spring_ai.intro.output.MusicArtist;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

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
    public MusicArtist getSimilarArtist() {
        MusicArtist musicArtist = chatClient.prompt()
                .user("Pink Floyd")
                .call()
                .entity(MusicArtist.class);
        return musicArtist;
    }

    @GetMapping("/music-artist-2")
    public MusicArtist getSimilarArtist2(@RequestBody String userInput) {
        MusicArtist musicArtist = chatClient.prompt()
                .user(u->u.text("Bana {userInput} gibi sanatçıları json listesi olarak verebilir misin").param("userInput", userInput))
                .call()
                .entity(MusicArtist.class);
        return musicArtist;
    }

}
