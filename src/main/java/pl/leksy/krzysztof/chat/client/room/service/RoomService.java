package pl.leksy.krzysztof.chat.client.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.leksy.krzysztof.chat.client.communication.model.http.CreateRoomRequestDto;
import pl.leksy.krzysztof.chat.client.communication.model.http.JoinRoomRequestDto;
import pl.leksy.krzysztof.chat.client.communication.model.http.RoomDto;
import pl.leksy.krzysztof.chat.client.communication.service.HttpClient;
import pl.leksy.krzysztof.chat.client.communication.service.WebSocketClient;
import pl.leksy.krzysztof.chat.client.room.model.Room;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService implements RoomFacade {
    private final HttpClient httpClient;
    private final WebSocketClient webSocketClient;

    @Override
    public String createChatRoom(Room room, String nickname) {
        LOGGER.info("Creating room {} for user called {}", room, nickname);
        final var request = new CreateRoomRequestDto()
                .setName(room.getName())
                .setNickname(nickname)
                .setPassword(room.getPassword())
                .setPasswordProtected(room.isPasswordProtected())
                .setSlots(room.getSlots());
        final var roomName = httpClient.createChatRoomOnServer(request).getRoomName();
        LOGGER.info("Created room with name {}", roomName);
        return roomName;
    }

    @Override
    public void joinChatRoom(Room room, String nickname) {
        LOGGER.info("Joining room {} by user called {}", room, nickname);
        final var request = new JoinRoomRequestDto()
                .setName(room.getName())
                .setNickname(nickname)
                .setPassword(room.getPassword());
        final var canJoin = httpClient.joinChatRoomOnServer(request);

        if (canJoin) {
            LOGGER.info("User {} can join room {} - proceeding further.", nickname, room.getName());
            webSocketClient.joinChatRoom(request);
        } else {
            LOGGER.warn("User {} cannot join room {} - leaving.", nickname, room.getName());
        }
    }

    @Override
    public List<RoomDto> listPublicRooms() {
        return httpClient.listPublicRooms()
                .getRooms();
    }
}
