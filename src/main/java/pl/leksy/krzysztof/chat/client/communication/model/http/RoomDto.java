package pl.leksy.krzysztof.chat.client.communication.model.http;

import lombok.Data;

@Data
public class RoomDto {
    private String name;
    private int slots;
    private int currentUsers;
    private String createdBy;
}
