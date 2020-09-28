package pl.leksy.krzysztof.chat.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import pl.leksy.krzysztof.chat.client.ui.CreateRoomRunner;
import pl.leksy.krzysztof.chat.client.ui.JoinRoomRunner;
import pl.leksy.krzysztof.chat.client.ui.MainMenuRunner;

@SpringBootApplication
@RequiredArgsConstructor
public class ChatClientApplication implements CommandLineRunner, ExitCodeGenerator {
    private final MainMenuRunner mainMenuRunner;
    private final CreateRoomRunner createRoomRunner;
    private final JoinRoomRunner joinRoomRunner;
    private final CommandLine.IFactory factory;

    private int exitCode;

    public static void main(String[] args) {
        SpringApplication.run(ChatClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final var commandLine = new CommandLine(mainMenuRunner, factory);
        commandLine.addSubcommand("create", createRoomRunner);
        commandLine.addSubcommand("join", joinRoomRunner);

        exitCode = commandLine.execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
