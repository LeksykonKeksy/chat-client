package pl.leksy.krzysztof.chat.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ChatClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatClientApplication.class, args);
    }
}
