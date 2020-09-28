package pl.leksy.krzysztof.chat.client.ui;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.leksy.krzysztof.chat.client.room.model.Room;
import pl.leksy.krzysztof.chat.client.room.service.RoomFacade;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Slf4j
@Service
@Setter
@Command(name = "join", description = "Joins existing chat room")
@RequiredArgsConstructor
public class JoinRoomRunner implements Runnable {
    private final RoomFacade roomFacade;

    @Option(names = {"-N", "-nickname"}, required = true)
    private String nickname;

    @Option(names = {"-I", "--id"}, required = true)
    private String roomName;

    @Option(names = {"-W", "--password"})
    private String password;

    @Override
    public void run() {
        final var existingRoom = new Room()
                .setName(roomName)
                .setPassword(password);
        roomFacade.joinChatRoom(existingRoom, nickname);
    }
}
