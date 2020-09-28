package pl.leksy.krzysztof.chat.client.communication.model.http;

import lombok.Data;

@Data
public class CreateRoomRequestDto {
    private String name;
    private boolean passwordProtected;
    private String password;
    private int slots;
    private String nickname;
}
