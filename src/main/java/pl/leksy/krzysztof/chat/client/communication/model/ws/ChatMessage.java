package pl.leksy.krzysztof.chat.client.communication.model.ws;

import lombok.Data;

@Data
public class ChatMessage {
    private String from;
    private String text;
}
