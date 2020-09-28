package pl.leksy.krzysztof.chat.client.communication.model.http;

import lombok.Data;

@Data
public class JoinRoomRequestDto {
    private String name;
    private String password;
    private String nickname;
}
