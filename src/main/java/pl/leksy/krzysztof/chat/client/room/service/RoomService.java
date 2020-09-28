package pl.leksy.krzysztof.chat.client.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.leksy.krzysztof.chat.client.room.model.Room;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService implements RoomFacade {

    @Override
    public void createChatRoom(Room room, String nickname) {
        System.out.println(String.format("Creating room %s for user called %s", room, nickname));
        LOGGER.info("Creating room {} for user called {}", room, nickname);
    }

    @Override
    public void joinChatRoom(Room room, String nickname) {
        LOGGER.info("Joining room {} by user called {}", room, nickname);
    }

    @Override
    public List<Room> listPublicRooms() {
        return null;
    }
}
