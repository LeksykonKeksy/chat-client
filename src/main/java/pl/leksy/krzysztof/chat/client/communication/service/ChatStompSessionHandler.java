package pl.leksy.krzysztof.chat.client.communication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import pl.leksy.krzysztof.chat.client.communication.model.ws.ChatMessage;

import java.lang.reflect.Type;

@Slf4j
@RequiredArgsConstructor
public class ChatStompSessionHandler implements StompSessionHandler {
    private final String chatTopic;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(chatTopic, this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        final var msg = (ChatMessage) payload;
        LOGGER.info("[{}]: {}", msg.getFrom(), msg.getText()); // TODO: kolorki?
    }
}
