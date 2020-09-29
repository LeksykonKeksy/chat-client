package pl.leksy.krzysztof.chat.client.communication.model.http;

import lombok.Data;

import java.util.List;

@Data
public class RoomListDto {
    private List<RoomDto> rooms;
}
