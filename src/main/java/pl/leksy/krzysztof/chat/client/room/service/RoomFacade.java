package pl.leksy.krzysztof.chat.client.room.service;

import pl.leksy.krzysztof.chat.client.communication.model.http.RoomDto;
import pl.leksy.krzysztof.chat.client.room.model.Room;

import java.util.List;

public interface RoomFacade {
    String createChatRoom(Room room, String nickname);

    void joinChatRoom(Room room, String nickname);

    List<RoomDto> listPublicRooms();
}
