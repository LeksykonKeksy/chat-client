package pl.leksy.krzysztof.chat.client.room.service;

import pl.leksy.krzysztof.chat.client.room.model.Room;

import java.util.List;

public interface RoomFacade {
    void createChatRoom(Room room, String nickname);

    void joinChatRoom(Room room, String nickname);

    List<Room> listPublicRooms();
}
