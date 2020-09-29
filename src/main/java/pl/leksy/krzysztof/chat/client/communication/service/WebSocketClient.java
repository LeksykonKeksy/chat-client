package pl.leksy.krzysztof.chat.client.communication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import pl.leksy.krzysztof.chat.client.communication.model.http.JoinRoomRequestDto;
import pl.leksy.krzysztof.chat.client.communication.model.ws.ChatMessage;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketClient {

    @Value("${chat.server.ws.url}")
    private String serverUrl;

    @Value("${chat.server.topic.name}")
    private String chatTopicName;

    @Value("${chat.server.msg.destination}")
    private String chatMessageDestination;

    private final static String EXIT_STRING = "/exit";
    private final Scanner scanner = new Scanner(System.in);

    public void joinChatRoom(JoinRoomRequestDto dto) {
        final var roomName = dto.getName();
        StompSession session;
        try {
            session = createStompSession(roomName).get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Cannot connect to chat {}. Leaving...", roomName);
            return;
        }

        LOGGER.info("Connected to chat {}. Type '/exit' to leave.", roomName);

        var inputMessage = scanner.nextLine();
        while (!EXIT_STRING.equalsIgnoreCase(inputMessage)) {
            session.send(createChatDestination(roomName), new ChatMessage()
                    .setFrom(dto.getNickname())
                    .setText(inputMessage));
            inputMessage = scanner.nextLine();
        }

        session.disconnect();
        LOGGER.info("Successfully disconnected chat session.");
    }

    private ListenableFuture<StompSession> createStompSession(String roomName) {
        final var client = new StandardWebSocketClient();
        final var stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        final var sessionHandler = new ChatStompSessionHandler(createChatTopicName(roomName));
        return stompClient.connect(serverUrl, sessionHandler);
    }

    private String createChatTopicName(String roomName) {
//        return chatTopicName + "/" + roomName;
        return chatTopicName;
    }

    private String createChatDestination(String roomName) {
//        return chatMessageDestination + "/" + roomName;
        return chatMessageDestination;
    }
}
