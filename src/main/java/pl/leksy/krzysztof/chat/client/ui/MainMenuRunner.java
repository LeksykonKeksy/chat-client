package pl.leksy.krzysztof.chat.client.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.leksy.krzysztof.chat.client.room.model.Room;
import pl.leksy.krzysztof.chat.client.room.service.RoomFacade;

import java.util.Scanner;

import static pl.leksy.krzysztof.chat.client.utils.PasswordHasher.hashPassword;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainMenuRunner implements CommandLineRunner {
    private final RoomFacade roomFacade;
    private final Scanner scanner = new Scanner(System.in);

    private final static String exitString = "exit";

    @Override
    public void run(String... args) {
        printLine("Welcome to chat app");

        String command;
        do {
            printLine("Type 'exit' to exit app");
            printLine("Would you 'join', 'create' or 'list' chat room(s)?");
            command = readLine();

            proceed(command);
        } while (!exitString.equalsIgnoreCase(command));
        printLine("Exiting chat client...");
    }

    private void proceed(String command) {
        switch (command) {
            case "join":
                join();
                break;
            case "create":
                create();
                break;
            case "list":
                list();
                break;
            case exitString:
                break;
            default:
                printLine("Unknown command - should be 'join', 'create' or 'list'");
        }
    }

    private void list() {
        final var rooms = roomFacade.listPublicRooms();
        for (int i = 1; i <= rooms.size(); ++i) {
            final var currRoom = rooms.get(i - 1);
            printLine("%d. \t%s\t%d/%d\t%s", i, currRoom.getName(),
                    currRoom.getCurrentUsers(), currRoom.getSlots(), currRoom.getCreatedBy());
        }
    }

    private void join() {
        printLine("Enter nickname: ");
        final var nickname = readLine();
        printLine("Enter room name/id: ");
        final var roomName = readLine();
        printLine("Enter room password (you can skip this field): ");
        final var password = hashPassword(readLine());
        // TODO: obsługa różnych haseł (enter, spacja, bazgroły itp)

        final var room = new Room()
                .setName(roomName)
                .setPassword(password);

        roomFacade.joinChatRoom(room, nickname);
    }

    private void create() {
        printLine("Enter nickname: ");
        final var nickname = readLine();
        printLine("Enter room name/id: ");
        final var roomName = readLine();
        printLine("Should room be password protected? (T/F): ");
        final var roomPrivate = readBooleanFromString(readLine());
        String password = null;
        if (roomPrivate) {
            printLine("Enter room password (you can skip this field): ");
            password = hashPassword(readLine());
        }
        printLine("How many slots should there be: ");
        final var slots = Integer.parseInt(readLine());

        final var room = new Room()
                .setName(roomName)
                .setPasswordProtected(roomPrivate)
                .setPassword(password)
                .setSlots(slots);

        final var finalRoomName = roomFacade.createChatRoom(room, nickname);
        room.setName(finalRoomName);

        printLine("Proceed to join to created room? (T/F): ");
        final var joinNewRoom = readBooleanFromString(readLine());
        if (joinNewRoom) {
            roomFacade.joinChatRoom(room, nickname);
        }

    }

    private boolean readBooleanFromString(String s) {
        switch (s) {
            case "T":
            case "t":
            case "true":
            case "True":
                return true;
            case "F":
            case "f":
            case "false":
            case "False":
                return false;
            default:
                printLine("Invalid T/N value. Using default - 'false'.");
                return false;
        }
    }

    private void printLine(String line, Object... args) {
        System.out.println(String.format(line, args));
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
