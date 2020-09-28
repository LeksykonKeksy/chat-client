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
@Command(name = "create", description = "Creates new chat room")
@RequiredArgsConstructor
public class CreateRoomRunner implements Runnable {
    private final RoomFacade roomFacade;

    @Option(names = {"-N", "-nickname"}, required = true)
    private String nickname;

    @Option(names = {"-I", "--id"})
    private String roomName;

    @Option(names = {"-P", "--private"}, required = true)
    private boolean roomPrivate;

    @Option(names = {"-W", "--password"})
    private String password;

    @Option(names = {"-S", "--slots"})
    private int slots;

    @Override
    public void run() {
        final var newRoom = new Room()
                .setName(roomName)
                .setPassword(password)
                .setPasswordProtected(roomPrivate)
                .setSlots(slots);
        roomFacade.createChatRoom(newRoom, nickname);
    }
}
