package pl.leksy.krzysztof.chat.client.communication.model.http;

import lombok.Data;

@Data
public class DisconnectFromRoomRequestDto {
    private String roomName;
}