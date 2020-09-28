package pl.leksy.krzysztof.chat.client.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.leksy.krzysztof.chat.client.room.model.Room;
import pl.leksy.krzysztof.chat.client.room.service.RoomFacade;

import java.util.Scanner;

import static picocli.CommandLine.Command;

@Slf4j
@Service
@RequiredArgsConstructor
@Command(name = "chat") // TODO
public class MainMenuRunner implements Runnable {
    private final RoomFacade roomFacade;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        printLine("Welcome to chat app");
        printLine("Would you 'join' or 'create' chat room?");
        final var command = readLine();

        proceed(command);
    }

    private void proceed(String command) {
        switch (command) {
            case "join":
                join();
                break;
            case "create":
                create();
                break;
            default:
                printLine("Unknown command - should be 'join' or 'create'");
        }
    }

    private void join() {
        printLine("Enter nickname: ");
        final var nickname = readLine();
        printLine("Enter room name/id: ");
        final var roomName = readLine();
        printLine("Enter room password (you can skip this field): ");
        final var password = readLine();
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
            password = readLine();
        }
        printLine("How many slots should there be: ");
        final var slots = Integer.parseInt(readLine());

        final var room = new Room()
                .setName(roomName)
                .setPasswordProtected(roomPrivate)
                .setPassword(password)
                .setSlots(slots);

        roomFacade.createChatRoom(room, nickname);

    }

    private boolean readBooleanFromString(String s) {
        switch (s) {
            case "T":
            case "t":
            case "true":
            case "tak":
            case "True":
                return true;
            case "N":
            case "n":
            case "no":
            case "nie":
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
